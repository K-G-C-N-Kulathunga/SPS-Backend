package com.it.sps.service;

import com.it.sps.dto.*;
import com.it.sps.entity.*;
import com.it.sps.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationWiringLDBackService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private WiringLandDetailRepository wiringLandDetailRepository;

    private static final String DEFAULT_DEPT_ID = "510.20";
    private static final String UPLOAD_DIR = "C:/SPS_Uploads/";

    @Transactional
    public String submitFullApplication(
            ApplicationFormRequestDTO formData,
            MultipartFile idCopy,
            MultipartFile ownershipCertificate,
            MultipartFile gramaNiladhariCertificate,
            MultipartFile engineerCertificate
    ) throws IOException {

        System.out.println(">>> STARTING SUBMISSION...");
        String currentDeptId = DEFAULT_DEPT_ID;

        try {
            FormApplicantDTO applicantDto = formData.getFormApplicantDto();
            FormApplicationDTO appDto = formData.getApplicationFormRequestDto();
            FormWiringLandDetailDTO wiringDto = formData.getFormWiringLandDetailDto();

            // 1. Retrieve Applicant
            if (applicantDto == null || applicantDto.getIdNo() == null) {
                throw new IllegalArgumentException("Applicant ID No is required.");
            }
            Applicant applicant = applicantRepository.findById(applicantDto.getIdNo())
                    .orElseThrow(() -> new EntityNotFoundException("Applicant not found with ID: " + applicantDto.getIdNo()));

            // 2. Process Application
            Application application = new Application();

            // --- NEW: Generate Custom ID and Number ---
            String newAppId = generateApplicationId(currentDeptId);
            String newAppNo = generateApplicationNo(currentDeptId);

            System.out.println(">>> Generated Application ID: " + newAppId);
            System.out.println(">>> Generated Application No: " + newAppNo);

            ApplicationPK appPk = new ApplicationPK();
            appPk.setApplicationId(newAppId);
            appPk.setDeptId(currentDeptId);
            application.setId(appPk);

            application.setApplicant(applicant);
            application.setApplicationNo(newAppNo); // Set the generated ENC number
            application.setSubmitDate(new Date());
            application.setStatus("P");

            // --- FIX: Application Defaults ---
            application.setApplicationType("NC");
            application.setPreparedBy("WEB_USER");

            if (applicantDto.getAppSubType() != null) {
                application.setApplicationSubType(applicantDto.getAppSubType());
            }

            application.setContactName(appDto.getContactName());
            application.setContactMobile(appDto.getContactMobile());
            application.setContactEmail(appDto.getContactEmail());
            application.setContactAddress(appDto.getContactAddress());
            application.setContactTelephone(appDto.getContactTelephone());
            application.setContactIdNo(appDto.getContactIdNo());

            application.setAddUser("WEB_USER");
            application.setAddDate(new Date());

            // Coordinates
            application.setLongitude(toBigDecimal(wiringDto.getLongitude()));
            application.setLatitude(toBigDecimal(wiringDto.getLatitude()));

            // --- SAVE APPLICATION ---
            try {
                applicationRepository.save(application);
                System.out.println(">>> Application Saved Successfully");
            } catch (Exception e) {
                System.err.println("!!! ERROR SAVING APPLICATION: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            // --- SAVE FILES ---
            try {
                String cleanAppId = newAppId.replace("/", "_");
                if (idCopy != null && !idCopy.isEmpty()) saveFileToDisk(idCopy, cleanAppId, "ID_COPY");
                if (ownershipCertificate != null && !ownershipCertificate.isEmpty()) saveFileToDisk(ownershipCertificate, cleanAppId, "OWNERSHIP");
                if (gramaNiladhariCertificate != null && !gramaNiladhariCertificate.isEmpty()) saveFileToDisk(gramaNiladhariCertificate, cleanAppId, "GRAMA");
                if (engineerCertificate != null && !engineerCertificate.isEmpty()) saveFileToDisk(engineerCertificate, cleanAppId, "ENGINEER");
                System.out.println(">>> Files Saved Successfully");
            } catch (Exception e) {
                System.err.println("!!! ERROR SAVING FILES: " + e.getMessage());
            }

            // 3. Process Wiring
            WiringLandDetail wiringEntity = new WiringLandDetail();
            WiringLandDetailPK wiringPk = new WiringLandDetailPK();
            wiringPk.setApplicationId(newAppId);
            wiringPk.setDeptId(currentDeptId);
            wiringEntity.setId(wiringPk);

            wiringEntity.setAssessmentNo(wiringDto.getAssessmentNo());
            wiringEntity.setNeighboursAccNo(wiringDto.getNeighboursAccNo());
            wiringEntity.setServiceStreetAddress(wiringDto.getServiceStreetAddress());
            wiringEntity.setServiceCity(wiringDto.getServiceCity());

            // --- FIX: Handle Numeric Conversions ---
            wiringEntity.setPhase(toBigDecimal(wiringDto.getPhase()));
            wiringEntity.setConnectionType(toBigDecimal(wiringDto.getConnectionType()));

            // --- FIX: Truncate Ownership (Max 1 Char) ---
            String ownership = wiringDto.getOwnership();
            if (ownership != null && ownership.length() > 1) {
                ownership = ownership.substring(0, 1);
            }
            wiringEntity.setOwnership(ownership);

            // --- FIX: Set Defaults for Mandatory Columns (NOT NULL constraints) ---
            wiringEntity.setOccupyOwnerCertified("N");
            wiringEntity.setIsGovernmentPlace("N");

            String tCat = wiringDto.getTariffCatCode();
            wiringEntity.setTariffCatCode((tCat != null && !tCat.isEmpty()) ? tCat : "DP");

            String tCode = wiringDto.getTariffCode();
            wiringEntity.setTariffCode((tCode != null && !tCode.isEmpty()) ? tCode : "11");

            String cCat = wiringDto.getCustomerCategory();
            wiringEntity.setCustomerCategory((cCat != null && !cCat.isEmpty()) ? cCat : "PRIV");

            String cType = wiringDto.getCustomerType();
            wiringEntity.setCustomerType((cType != null && !cType.isEmpty()) ? cType : "DOME");

            // --- SAVE WIRING ---
            try {
                wiringLandDetailRepository.save(wiringEntity);
                System.out.println(">>> Wiring Details Saved Successfully");
            } catch (Exception e) {
                System.err.println("!!! ERROR SAVING WIRING DETAILS: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            return newAppId;

        } catch (Exception e) {
            System.err.println("!!! TRANSACTION ROLLED BACK: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void updateExistingApplication(
            String refNo,
            ApplicationFormRequestDTO formData,
            MultipartFile idCopy,
            MultipartFile ownershipCertificate,
            MultipartFile gramaNiladhariCertificate,
            MultipartFile engineerCertificate
    ) throws IOException {

        System.out.println(">>> STARTING UPDATE for RefNo: " + refNo);
        String currentDeptId = DEFAULT_DEPT_ID;

        try {
            FormApplicantDTO applicantDto = formData.getFormApplicantDto();
            FormApplicationDTO appDto = formData.getApplicationFormRequestDto();
            FormWiringLandDetailDTO wiringDto = formData.getFormWiringLandDetailDto();

            // 1. UPDATE APPLICATION ENTITY
            ApplicationPK appPk = new ApplicationPK();
            appPk.setApplicationId(refNo);
            appPk.setDeptId(currentDeptId);

            Application application = applicationRepository.findById(appPk)
                    .orElseThrow(() -> new EntityNotFoundException("Application not found with ID: " + refNo));

            if (appDto != null) {
                application.setContactName(appDto.getContactName());
                application.setContactMobile(appDto.getContactMobile());
                application.setContactEmail(appDto.getContactEmail());
                application.setContactAddress(appDto.getContactAddress());
                application.setContactTelephone(appDto.getContactTelephone());
                application.setContactIdNo(appDto.getContactIdNo());
            }

            if (applicantDto != null && applicantDto.getAppSubType() != null) {
                application.setApplicationSubType(applicantDto.getAppSubType());
            }

            if (wiringDto != null) {
                application.setLongitude(toBigDecimal(wiringDto.getLongitude()));
                application.setLatitude(toBigDecimal(wiringDto.getLatitude()));
            }

            application.setUpdUser("WEB_USER");
            application.setUpdDate(new Date());

            applicationRepository.save(application);
            System.out.println(">>> Application Entity Updated Successfully");

            // 2. UPDATE FILES
            try {
                String cleanAppId = refNo.replace("/", "_");
                if (idCopy != null && !idCopy.isEmpty()) saveFileToDisk(idCopy, cleanAppId, "ID_COPY");
                if (ownershipCertificate != null && !ownershipCertificate.isEmpty()) saveFileToDisk(ownershipCertificate, cleanAppId, "OWNERSHIP");
                if (gramaNiladhariCertificate != null && !gramaNiladhariCertificate.isEmpty()) saveFileToDisk(gramaNiladhariCertificate, cleanAppId, "GRAMA");
                if (engineerCertificate != null && !engineerCertificate.isEmpty()) saveFileToDisk(engineerCertificate, cleanAppId, "ENGINEER");
                System.out.println(">>> Files Updated (if provided)");
            } catch (Exception e) {
                System.err.println("!!! ERROR UPDATING FILES: " + e.getMessage());
            }

            // 3. UPDATE WIRING LAND DETAILS
            if (wiringDto != null) {
                WiringLandDetailPK wiringPk = new WiringLandDetailPK();
                wiringPk.setApplicationId(refNo);
                wiringPk.setDeptId(currentDeptId);

                WiringLandDetail wiringEntity = wiringLandDetailRepository.findById(wiringPk)
                        .orElseThrow(() -> new EntityNotFoundException("Wiring Details not found for ID: " + refNo));

                wiringEntity.setAssessmentNo(wiringDto.getAssessmentNo());
                wiringEntity.setNeighboursAccNo(wiringDto.getNeighboursAccNo());
                wiringEntity.setServiceStreetAddress(wiringDto.getServiceStreetAddress());
                wiringEntity.setServiceCity(wiringDto.getServiceCity());
                wiringEntity.setPhase(toBigDecimal(wiringDto.getPhase()));
                wiringEntity.setConnectionType(toBigDecimal(wiringDto.getConnectionType()));

                String ownership = wiringDto.getOwnership();
                if (ownership != null && ownership.length() > 1) {
                    ownership = ownership.substring(0, 1);
                }
                wiringEntity.setOwnership(ownership);

                if (wiringDto.getTariffCatCode() != null) wiringEntity.setTariffCatCode(wiringDto.getTariffCatCode());
                if (wiringDto.getTariffCode() != null) wiringEntity.setTariffCode(wiringDto.getTariffCode());
                if (wiringDto.getCustomerCategory() != null) wiringEntity.setCustomerCategory(wiringDto.getCustomerCategory());
                if (wiringDto.getCustomerType() != null) wiringEntity.setCustomerType(wiringDto.getCustomerType());

                wiringLandDetailRepository.save(wiringEntity);
                System.out.println(">>> Wiring Details Updated Successfully");
            }

        } catch (Exception e) {
            System.err.println("!!! UPDATE TRANSACTION ROLLED BACK: " + e.getMessage());
            throw e;
        }
    }

    // =========================================================================
    // ID AND NUMBER GENERATION LOGIC (Migrated from ApplicationWiringLDService)
    // =========================================================================

    // Generate Application ID: {deptId}/ANC/{YY}/{Sequence}
    private String generateApplicationId(String deptId) {
        // "ANC" is hardcoded here based on your previous 'prefix' usage
        String sequence = getNextAppId(deptId,"app");
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s/ANC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
    }

    // Generate Application No: {deptId}/ENC/{YY}/{Sequence}
    private String generateApplicationNo(String deptId) {
        String sequence = getNextApplicationNo(deptId, "app");
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s/ENC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
    }

    @SuppressWarnings("unchecked")
    public String getNextAppId(String appIdPrefix, String webAppName) {
        String sequence = null;
        String like = appIdPrefix + "%";
        // Query to get the last ID from Application Table
        String strQuery = "select a.id.applicationId from Application a " +
                "where a.id.applicationId like :like ORDER BY 1 DESC";
        Query query = entityManager.createQuery(strQuery);
        query.setParameter("like", like);
        List<String> list = query.getResultList();

        if (list.size() != 0) {
            sequence = list.get(0).toString().trim();
            // Assuming strict format length for substring.
            // 510.20/ANC/25/0001 -> 14th index starts the number
            if(sequence.length() > 14) {
                sequence = sequence.substring(14);
                Integer i = Integer.parseInt(sequence) + 1;
                sequence = i.toString();
            } else {
                sequence = "1"; // Fallback if format is unexpected
            }
        } else {
            sequence = "0001";
        }

        return padSequence(sequence);
    }

    @SuppressWarnings("unchecked")
    public String getNextApplicationNo(String applicationNoPrefix, String webAppName) {
        String sequence = null;
        String like = applicationNoPrefix + "%";
        // Native Query to get last No from APPLICATIONS Table
        String strQuery = "select APPLICATION_NO from APPLICATIONS where APPLICATION_NO like '" + like + "' ORDER BY 1 DESC";

        Query query = entityManager.createNativeQuery(strQuery);
        List<String> list = query.getResultList();

        if (list.size() != 0) {
            sequence = list.get(0).toString().trim();
            // Assuming strict format length for substring
            if(sequence.length() > 14) {
                sequence = sequence.substring(14);
                Integer i = Integer.parseInt(sequence) + 1;
                sequence = i.toString();
            } else {
                sequence = "1";
            }
        } else {
            sequence = "0001";
        }

        return padSequence(sequence);
    }

    // Helper to pad with zeros
    private String padSequence(String sequence) {
        if (sequence.length() == 1) return "000" + sequence;
        else if (sequence.length() == 2) return "00" + sequence;
        else if (sequence.length() == 3) return "0" + sequence;
        else return sequence;
    }

    // =========================================================================
    // UTILITIES
    // =========================================================================

    private String saveFileToDisk(MultipartFile file, String appId, String docType) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String newFilename = appId + "_" + docType + extension;
        Path filePath = Paths.get(UPLOAD_DIR + newFilename);
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }

    private BigDecimal toBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            System.err.println("!!! Invalid Number: " + value);
            return null;
        }
    }
}