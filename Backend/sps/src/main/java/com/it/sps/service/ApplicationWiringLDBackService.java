package com.it.sps.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.it.sps.dto.*;
import com.it.sps.dto.ApplicationReferenceDto;

import com.it.sps.entity.*;
import com.it.sps.entity.ApplicationReference;
import com.it.sps.entity.ApplicationReferencePK;
import com.it.sps.entity.Dashboard;
import com.it.sps.entity.WiringLandDetail;
import com.it.sps.entity.WiringLandDetailPK;

import com.it.sps.repository.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.ZonedDateTime;


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
    @Autowired
    private ApplicationReferenceRepository applicationReferenceRepository;
    @Autowired
    private DashboardCustomRepository dashboardCustomRepository;


    @Autowired
    private DashboardRepository dashboardRepository;


    private static final String DEFAULT_DEPT_ID = "510.20"; //423.10
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
            application.setStatus("C");

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

            // ===== APPLICATION_REFERENCE =====
            ApplicationReference appRef = new ApplicationReference();
            ApplicationReferencePK appRefPK = new ApplicationReferencePK();
            appRefPK.setApplicationId(application.getId().getApplicationId());
            appRefPK.setDeptId(application.getId().getDeptId());
            appRef.setId(appRefPK);
            appRef.setApplicationNo(application.getApplicationNo());
            appRef.setIdNo(applicantDto.getIdNo());
            appRef.setStatus(application.getStatus()); // "N"
            appRef.setPostedBy(application.getPreparedBy());
            appRef.setPostedDate(LocalDate.now());
            appRef.setPostedTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            applicationReferenceRepository.save(appRef);

            // ===== DASHBOARD snapshot =====
            Dashboard dashboard = new Dashboard();
            dashboard.setApplicationId(newAppId);
            dashboard.setDeptId(currentDeptId);
            dashboard.setIdNo(applicant.getIdNo());

            String addr = String.join(", ",
                    nullSafe(applicant.getStreetAddress()),
                    nullSafe(applicant.getSuburb()),
                    nullSafe(applicant.getCity())
            ).replaceAll(", +$", "");
            if (addr.length() > 100) addr = addr.substring(0, 100);
            dashboard.setConsumerName(applicant.getFullName() != null ? applicant.getFullName() : "");
            dashboard.setConsumerAddress(addr);

            dashboard.setApplicationType("NC");
            dashboard.setApplicationSubType("PM");
            dashboard.setLoanType("N");

            FormWiringLandDetailDTO wld = formData.getFormWiringLandDetailDto();

            // We keep a snapshot; phase might be null on creation → default false
            dashboard.setPhase(Boolean.FALSE);
            Short connType = (wld != null && wld.getConnectionType() != null)
                    ? Short.valueOf(wld.getConnectionType())
                    : 1;
            dashboard.setConnectionType(connType);
            String tariffCat = (wld != null && wld.getTariffCatCode() != null) ? wld.getTariffCatCode() : "01";
            String tariff = (wld != null && wld.getTariffCode() != null) ? wld.getTariffCode() : "01";
            dashboard.setTariffCatCode(tariffCat);
            dashboard.setTariffCode(tariff);

            Instant startOfDay = LocalDate.now()
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant();
            dashboard.setAppSubmittedDate(startOfDay);
            dashboard.setAppSubmittedBy("online");
            dashboard.setStatus(application.getStatus()); // "N"
            dashboard.setStatusChangedDate(startOfDay);
            dashboard.setStatusChangedBy("online");
            dashboard.setStatusChangedReason("Submitted online");
            dashboard.setOriginatedBy("WEB");
            dashboard.setOnlineAppNo(application.getApplicationNo());

            dashboardRepository.save(dashboard);


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
    public String updateExistingApplication(
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

            // 1. Retrieve existing Application
            ApplicationPK appPk = new ApplicationPK();
            appPk.setApplicationId(refNo);
            appPk.setDeptId(currentDeptId);

            Application application = applicationRepository.findById(appPk)
                    .orElseThrow(() -> new EntityNotFoundException("Application not found with ID: " + refNo));

            // 2. Update Application details
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
            System.out.println(">>> Application updated successfully");

            // 3. Update WiringLandDetail
            WiringLandDetailPK wiringPk = new WiringLandDetailPK();
            wiringPk.setApplicationId(refNo);
            wiringPk.setDeptId(currentDeptId);

            WiringLandDetail wiringEntity = wiringLandDetailRepository.findById(wiringPk)
                    .orElseThrow(() -> new EntityNotFoundException("Wiring Details not found for ID: " + refNo));

            if (wiringDto != null) {
                wiringEntity.setAssessmentNo(wiringDto.getAssessmentNo());
                wiringEntity.setNeighboursAccNo(wiringDto.getNeighboursAccNo());
                wiringEntity.setServiceStreetAddress(wiringDto.getServiceStreetAddress());
                wiringEntity.setServiceCity(wiringDto.getServiceCity());

                // Convert numeric values safely
                wiringEntity.setPhase(toBigDecimal(wiringDto.getPhase()));
                wiringEntity.setConnectionType(toBigDecimal(wiringDto.getConnectionType()));

                // Trim ownership
                String ownership = wiringDto.getOwnership();
                if (ownership != null && ownership.length() > 1) ownership = ownership.substring(0, 1);
                wiringEntity.setOwnership(ownership);

                // Defaults if null
                wiringEntity.setTariffCatCode((wiringDto.getTariffCatCode() != null) ? wiringDto.getTariffCatCode() : "DP");
                wiringEntity.setTariffCode((wiringDto.getTariffCode() != null) ? wiringDto.getTariffCode() : "11");
                wiringEntity.setCustomerCategory((wiringDto.getCustomerCategory() != null) ? wiringDto.getCustomerCategory() : "PRIV");
                wiringEntity.setCustomerType((wiringDto.getCustomerType() != null) ? wiringDto.getCustomerType() : "DOME");
            }

            wiringLandDetailRepository.save(wiringEntity);
            System.out.println(">>> WiringLandDetail updated successfully");

            // 4. Update Dashboard snapshot
            Dashboard dashboard = dashboardCustomRepository
                    .findByApplicationIdAndDeptId(refNo, currentDeptId)
                    .orElse(new Dashboard());

            dashboard.setApplicationId(refNo);
            dashboard.setDeptId(currentDeptId);
            dashboard.setIdNo(applicantDto.getIdNo());
            dashboard.setConsumerName(applicantDto.getFullName() != null ? applicantDto.getFullName() : "");
            String addr = String.join(", ",
                    nullSafe(applicantDto.getStreetAddress()),
                    nullSafe(applicantDto.getSuburb()),
                    nullSafe(applicantDto.getCity())
            ).replaceAll(", +$", "");
            if (addr.length() > 100) addr = addr.substring(0, 100);
            dashboard.setConsumerAddress(addr);

            dashboard.setApplicationType("NC");
            dashboard.setApplicationSubType("PM");
            dashboard.setLoanType("N");

            if (wiringDto != null) {
                dashboard.setPhase(Boolean.FALSE);
                BigDecimal conn = toBigDecimal(wiringDto.getConnectionType());
                if (conn != null) {
                    dashboard.setConnectionType(conn.shortValue());
                }
                dashboard.setTariffCatCode(wiringDto.getTariffCatCode() != null ? wiringDto.getTariffCatCode() : "01");
                dashboard.setTariffCode(wiringDto.getTariffCode() != null ? wiringDto.getTariffCode() : "01");
            }

            // FIX: Use Date or LocalDate instead of Instant for Oracle DATE compatibility
            // Convert to java.util.Date for Oracle DATE field
            Instant startOfDay = LocalDate.now()
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant();
            dashboard.setAppSubmittedDate(startOfDay);
            dashboard.setAppSubmittedBy("online");
            dashboard.setStatus(application.getStatus());
            dashboard.setStatusChangedDate(startOfDay);
            dashboard.setStatusChangedBy("online");
            dashboard.setStatusChangedReason("Updated online");
            dashboard.setOriginatedBy("WEB");
            dashboard.setOnlineAppNo(application.getApplicationNo());

            dashboardRepository.save(dashboard);
            System.out.println(">>> Dashboard updated successfully");

            // 5. Update files
            String cleanAppId = refNo.replace("/", "_");
            if (idCopy != null && !idCopy.isEmpty()) saveFileToDisk(idCopy, cleanAppId, "ID_COPY");
            if (ownershipCertificate != null && !ownershipCertificate.isEmpty()) saveFileToDisk(ownershipCertificate, cleanAppId, "OWNERSHIP");
            if (gramaNiladhariCertificate != null && !gramaNiladhariCertificate.isEmpty()) saveFileToDisk(gramaNiladhariCertificate, cleanAppId, "GRAMA");
            if (engineerCertificate != null && !engineerCertificate.isEmpty()) saveFileToDisk(engineerCertificate, cleanAppId, "ENGINEER");
            System.out.println(">>> Files updated successfully");

            return refNo;

        } catch (Exception e) {
            System.err.println("!!! UPDATE TRANSACTION ROLLED BACK: " + e.getMessage());
            e.printStackTrace();
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

    // ===== Sequences / IDs =====

    @SuppressWarnings("unchecked")
    public String getNextAppId(String appIdPrefix, String webAppName) {
        String sequence = null;
        String like = appIdPrefix + "%";
        String strQuery = "select a.id.applicationId from Application a where a.id.applicationId like :like ORDER BY 1 DESC";
        Query query = entityManager.createQuery(strQuery);
        query.setParameter("like", like);
        List<String> list = query.getResultList();
        if (!list.isEmpty()) {
            sequence = list.get(0).trim();
            sequence = sequence.substring(14); // tail number
            Integer i = Integer.parseInt(sequence) + 1;
            sequence = i.toString();
        } else {
            sequence = "0001";
        }
        if (sequence.length() == 1) return "000" + sequence;
        else if (sequence.length() == 2) return "00" + sequence;
        else if (sequence.length() == 3) return "0" + sequence;
        else return sequence;
    }

    @SuppressWarnings("unchecked")
    public String getNextApplicationNo(String applicationNoPrefix, String webAppName) {
        String sequence = null;
        String like = applicationNoPrefix + "%";
        String strQuery = "select APPLICATION_NO from APPLICATIONS where APPLICATION_NO like '" + like + "' ORDER BY 1 DESC";
        Query query = entityManager.createNativeQuery(strQuery);
        List<String> list = query.getResultList();
        if (!list.isEmpty()) {
            sequence = list.get(0).trim();
            sequence = sequence.substring(14); // tail number
            Integer i = Integer.parseInt(sequence) + 1;
            sequence = i.toString();
        } else {
            sequence = "0001";
        }
        if (sequence.length() == 1) return "000" + sequence;
        else if (sequence.length() == 2) return "00" + sequence;
        else if (sequence.length() == 3) return "0" + sequence;
        else return sequence;
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

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

}