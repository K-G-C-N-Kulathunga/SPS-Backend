package com.it.sps.service;

import com.it.sps.dto.ApplicantDTO;
import com.it.sps.dto.ApplicationDTO;
import com.it.sps.dto.FormDataDto;
import com.it.sps.dto.WiringLandDetailDto;
import com.it.sps.dto.ApplicationFormRequestDTO;

import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;
import com.it.sps.entity.ApplicationReference;
import com.it.sps.entity.ApplicationReferencePK;
import com.it.sps.entity.Dashboard;
import com.it.sps.entity.WiringLandDetail;
import com.it.sps.entity.WiringLandDetailPK;

import com.it.sps.repository.ApplicantRepository;
import com.it.sps.repository.ApplicationReferenceRepository;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.DashboardRepository;
import com.it.sps.repository.OnlineApplicationRepository;
import com.it.sps.repository.WiringLandDetailRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ApplicationWiringLDBackService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationRepository applicationRepository;
    private final WiringLandDetailRepository wiringLandDetailRepository;
    private final ApplicationReferenceRepository applicationReferenceRepository;
    private final DashboardRepository dashboardRepository;
    private final ApplicantRepository applicantRepository;
    private final OnlineApplicationRepository onlineApplicationRepository;

    private static final String UPLOAD_DIR = "C:/SPS_Uploads/";

    public ApplicationWiringLDBackService(
            ApplicationRepository applicationRepository,
            WiringLandDetailRepository wiringLandDetailRepository,
            ApplicantRepository applicantRepository,
            ApplicationReferenceRepository applicationReferenceRepository,
            DashboardRepository dashboardRepository,
            OnlineApplicationRepository onlineApplicationRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.wiringLandDetailRepository = wiringLandDetailRepository;
        this.applicantRepository = applicantRepository;
        this.applicationReferenceRepository = applicationReferenceRepository;
        this.dashboardRepository = dashboardRepository;
        this.onlineApplicationRepository = onlineApplicationRepository;
    }

    public String submitFullApplication(ApplicationFormRequestDTO formData,
                                        MultipartFile idCopy,
                                        MultipartFile ownershipCertificate,
                                        MultipartFile gramaNiladhariCertificate,
                                        MultipartFile engineerCertificate,
                                        String tempId) throws IOException {

        // ===== Generate IDs =====
        String deptId = formData.getApplicationDto().getDeptId();
        String applicationId = generateApplicationId(deptId);
        String applicationNo = generateApplicationNo(deptId);

        formData.getApplicationDto().setApplicationId(applicationId);
        formData.getApplicationDto().setApplicationNo(applicationNo);
        formData.getWiringLandDetailDto().setApplicationId(applicationId);
        formData.getWiringLandDetailDto().setDeptId(deptId);

        // ===== Applicant =====
        Applicant applicant = createApplicant(formData.getApplicantDto());

        // ===== APPLICATIONS =====
        Application application = new Application();
        ApplicationPK applicationPK = new ApplicationPK();
        applicationPK.setApplicationId(applicationId);
        applicationPK.setDeptId(deptId);
        application.setId(applicationPK);

        // Backend-owned fields
        application.setApplicationType("NC");
        application.setApplicationSubType("PM");
        application.setConfirmedBy("online");
        application.setStatus("N");
        application.setAddUser("online");
        application.setAddDate(new Date());
        application.setAddTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        application.setIsLoanApp("N");
        application.setSamurdhiMember("N");
        application.setIsVisitngNeeded("Y");
        application.setUpdUser(null);
        application.setUpdDate(null);
        application.setUpdTime(null);

        // References / dates
        application.setApplicationNo(applicationNo);
        application.setSubmitDate(new Date());
        application.setPreparedBy("ONLINE");

        // Relations
        application.setApplicant(applicant);

        // Contact info
        ApplicationDTO aDto = formData.getApplicationDto();
        application.setContactIdNo(aDto.getContactIdNo());
        application.setContactName(aDto.getContactName());
        application.setContactAddress(aDto.getContactAddress());
        application.setContactTelephone(aDto.getContactTelephone());
        application.setContactEmail(aDto.getContactEmail());
        application.setContactMobile(aDto.getContactMobile());

        applicationRepository.save(application);

        // ===== UPDATE FILES =====
        try {
            String cleanAppId = applicationId.replace("/", "_");

            if (idCopy != null && !idCopy.isEmpty()) saveFileToDisk(idCopy, cleanAppId, "ID_COPY");
            if (ownershipCertificate != null && !ownershipCertificate.isEmpty()) saveFileToDisk(ownershipCertificate, cleanAppId, "OWNERSHIP");
            if (gramaNiladhariCertificate != null && !gramaNiladhariCertificate.isEmpty()) saveFileToDisk(gramaNiladhariCertificate, cleanAppId, "GRAMA");
            if (engineerCertificate != null && !engineerCertificate.isEmpty()) saveFileToDisk(engineerCertificate, cleanAppId, "ENGINEER");
            System.out.println(">>> Files Updated (if provided)");
        } catch (Exception e) {
            System.err.println("!!! ERROR UPDATING FILES: " + e.getMessage());
        }

        // ===== WIRING_LAND_DETAIL =====
        saveWiringDetail(formData.getWiringLandDetailDto());

        // ===== APPLICATION_REFERENCE =====
        ApplicationReference appRef = new ApplicationReference();
        ApplicationReferencePK appRefPK = new ApplicationReferencePK();
        appRefPK.setApplicationId(application.getId().getApplicationId());
        appRefPK.setDeptId(application.getId().getDeptId());
        appRef.setId(appRefPK);
        appRef.setApplicationNo(application.getApplicationNo());
        appRef.setIdNo(formData.getApplicantDto().getIdNo());
        appRef.setStatus(application.getStatus());
        appRef.setPostedBy(application.getPreparedBy());
        appRef.setPostedDate(LocalDate.now());
        appRef.setPostedTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        applicationReferenceRepository.save(appRef);

        // ===== DASHBOARD SNAPSHOT =====
        Dashboard dashboard = new Dashboard();
        dashboard.setApplicationId(applicationId);
        dashboard.setDeptId(deptId);
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

        WiringLandDetailDto wld = formData.getWiringLandDetailDto();
        dashboard.setPhase(Boolean.FALSE);
        Short connType = (wld != null && wld.getConnectionType() != null)
                ? wld.getConnectionType().shortValue()
                : Short.valueOf((short) 1);
        dashboard.setConnectionType(connType);
        String tariffCat = (wld != null && wld.getTariffCatCode() != null) ? wld.getTariffCatCode() : "01";
        String tariff = (wld != null && wld.getTariffCode() != null) ? wld.getTariffCode() : "01";
        dashboard.setTariffCatCode(tariffCat);
        dashboard.setTariffCode(tariff);

        dashboard.setAppSubmittedDate(new Date());
        dashboard.setAppSubmittedBy("online");
        dashboard.setStatus(application.getStatus());
        dashboard.setStatusChangedDate(new Date());
        dashboard.setStatusChangedBy("online");
        dashboard.setStatusChangedReason("Submitted online");
        dashboard.setOriginatedBy("WEB");
        dashboard.setOnlineAppNo(application.getApplicationNo());

        dashboardRepository.save(dashboard);

        // ===== REMOVED: Update ONLINE_APPLICATION draft =====
        // Since you don't need to save to online_application table, just skip this
        // updateOnlineDraft(tempId, applicationNo);

        return applicationNo;
    }

    /** Backward-compatible overload. */
    public String saveFullApplication(FormDataDto formData) {
        try {
            // Need to convert FormDataDto to ApplicationFormRequestDTO manually or restructure
            // For now returning null or handling simple save if logic allows
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String updateExistingApplication(String refNo,
                                            ApplicationFormRequestDTO formData,
                                            MultipartFile idCopy,
                                            MultipartFile ownershipCertificate,
                                            MultipartFile gramaNiladhariCertificate,
                                            MultipartFile engineerCertificate,
                                            String tempId) throws IOException {

        // TODO: Implement update logic
        System.out.println("Updating application with refNo: " + refNo);

        // For now, just return the refNo
        return refNo;
    }

    // ===== Helpers =====

    // REMOVED: updateOnlineDraft method since you don't need it

    private static String nullSafe(String v) {
        return v == null ? "" : v.trim();
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
            sequence = sequence.substring(14);
            Integer i = Integer.parseInt(sequence) + 1;
            sequence = i.toString();
        } else {
            sequence = "0001";
        }
        return padSequence(sequence);
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
            sequence = sequence.substring(14);
            Integer i = Integer.parseInt(sequence) + 1;
            sequence = i.toString();
        } else {
            sequence = "0001";
        }
        return padSequence(sequence);
    }

    private String padSequence(String sequence) {
        if (sequence.length() == 1) return "000" + sequence;
        else if (sequence.length() == 2) return "00" + sequence;
        else if (sequence.length() == 3) return "0" + sequence;
        else return sequence;
    }

    private String generateApplicationId(String deptId) {
        String sequence = getNextAppId(deptId, "app");
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s/ANC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
    }

    private String generateApplicationNo(String deptId) {
        String sequence = getNextApplicationNo(deptId, "app");
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s/ENC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
    }

    public void saveWiringDetail(WiringLandDetailDto wiringLandDetailDto) {
        WiringLandDetail wiringLandDetail = new WiringLandDetail();
        WiringLandDetailPK wiringLandDetailPK = new WiringLandDetailPK();
        wiringLandDetailPK.setApplicationId(wiringLandDetailDto.getApplicationId());
        wiringLandDetailPK.setDeptId(wiringLandDetailDto.getDeptId());
        wiringLandDetail.setId(wiringLandDetailPK);

        wiringLandDetail.setServiceStreetAddress(wiringLandDetailDto.getServiceStreetAddress());
        wiringLandDetail.setServiceCity(wiringLandDetailDto.getServiceCity());
        wiringLandDetail.setServiceSuburb(wiringLandDetailDto.getServiceSuburb());
        wiringLandDetail.setServicePostalCode(wiringLandDetailDto.getServicePostalCode());
        wiringLandDetail.setAssessmentNo(wiringLandDetailDto.getAssessmentNo());
        wiringLandDetail.setNeighboursAccNo(wiringLandDetailDto.getNeighboursAccNo());
        wiringLandDetail.setOwnership("O");
        wiringLandDetail.setIsGovernmentPlace("N");
        wiringLandDetail.setOccupyOwnerCertified("Y");

        wiringLandDetail.setMetalCrusher(wiringLandDetailDto.getMetalCrusher());
        wiringLandDetail.setSawMills(wiringLandDetailDto.getSawMills());
        wiringLandDetail.setWeldingPlant(wiringLandDetailDto.getWeldingPlant());
        wiringLandDetail.setPhase(wiringLandDetailDto.getPhase());
        wiringLandDetail.setCustomerCategory(wiringLandDetailDto.getCustomerCategory());
        wiringLandDetail.setTariffCatCode(wiringLandDetailDto.getTariffCatCode());
        wiringLandDetail.setTariffCode(wiringLandDetailDto.getTariffCode());
        wiringLandDetail.setConnectionType(wiringLandDetailDto.getConnectionType());
        wiringLandDetail.setCustomerType(wiringLandDetailDto.getCustomerType());

        wiringLandDetailRepository.save(wiringLandDetail);
    }

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

    public Applicant createApplicant(ApplicantDTO applicantDto) {
        Applicant applicant = new Applicant();
        applicant.setIdNo(applicantDto.getIdNo());
        applicant.setIdType(applicantDto.getIdType());
        applicant.setPersonalCorporate(applicantDto.getPersonalCorporate());
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setFullName(applicantDto.getFullName());
        applicant.setTelephoneNo(applicantDto.getTelephoneNo());
        applicant.setEmail(applicantDto.getEmail());
        applicant.setMobileNo(applicantDto.getMobileNo());
        applicant.setCity(applicantDto.getCity());
        applicant.setSuburb(applicantDto.getSuburb());
        applicant.setStreetAddress(applicantDto.getStreetAddress());
        applicant.setPostalCode(applicantDto.getPostalCode());
        applicant.setPreferredLanguage(applicantDto.getPreferredLanguage());

        applicant.setAddUser("online");
        applicant.setAddDate(new Date());
        applicant.setAddTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));

        return applicantRepository.save(applicant);
    }

    public void saveContactPersonDetail(ApplicationDTO applicationDto, Applicant applicant) {
        Application application = new Application();
        ApplicationPK applicationPK = new ApplicationPK();
        applicationPK.setApplicationId(applicationDto.getApplicationId());
        applicationPK.setDeptId(applicationDto.getDeptId());
        application.setId(applicationPK);

        // backend-owned
        application.setApplicationType("NC");
        application.setApplicationSubType("PM");
        application.setConfirmedBy("online");
        application.setStatus("N");
        application.setAddUser("online");
        application.setAddDate(new Date());
        application.setAddTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        application.setIsLoanApp("N");
        application.setSamurdhiMember("N");
        application.setIsVisitngNeeded("Y");

        application.setApplicationNo(applicationDto.getApplicationNo());
        application.setSubmitDate(new Date());
        application.setPreparedBy("ONLINE");
        application.setApplicant(applicant);

        application.setContactIdNo(applicationDto.getContactIdNo());
        application.setContactName(applicationDto.getContactName());
        application.setContactAddress(applicationDto.getContactAddress());
        application.setContactTelephone(applicationDto.getContactTelephone());
        application.setContactEmail(applicationDto.getContactEmail());
        application.setContactMobile(applicationDto.getContactMobile());

        applicationRepository.save(application);
    }
}