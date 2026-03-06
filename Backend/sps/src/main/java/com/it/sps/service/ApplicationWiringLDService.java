package com.it.sps.service;

import com.it.sps.dto.ApplicantDTO;
import com.it.sps.dto.ApplicationDTO;
import com.it.sps.dto.FormDataDto;
import com.it.sps.dto.WiringLandDetailDto;

import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;
import com.it.sps.entity.ApplicationReference;
import com.it.sps.entity.ApplicationReferencePK;
import com.it.sps.entity.Dashboard;
import com.it.sps.entity.OnlineApplication;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Persists APPLICATIONS + related rows from a full form submission.
 * Also updates ONLINE_APPLICATION (when tempId is provided)
 * and sends final SMS with the generated application number.
 */
@Service
@Transactional
public class ApplicationWiringLDService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationWiringLDService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationRepository applicationRepository;
    private final WiringLandDetailRepository wiringLandDetailRepository;
    private final ApplicationReferenceRepository applicationReferenceRepository;
    private final DashboardRepository dashboardRepository;
    private final ApplicantRepository applicantRepository;
    private final OnlineApplicationRepository onlineApplicationRepository;
    private final SmsService smsService;

    public ApplicationWiringLDService(
            ApplicationRepository applicationRepository,
            WiringLandDetailRepository wiringLandDetailRepository,
            ApplicantRepository applicantRepository,
            ApplicationReferenceRepository applicationReferenceRepository,
            DashboardRepository dashboardRepository,
            OnlineApplicationRepository onlineApplicationRepository,
            SmsService smsService
    ) {
        this.applicationRepository = applicationRepository;
        this.wiringLandDetailRepository = wiringLandDetailRepository;
        this.applicantRepository = applicantRepository;
        this.applicationReferenceRepository = applicationReferenceRepository;
        this.dashboardRepository = dashboardRepository;
        this.onlineApplicationRepository = onlineApplicationRepository;
        this.smsService = smsService;
    }

    /**
     * Main entry used by /application endpoints when a tempId is known.
     * Persists APPLICATIONS + related rows, updates ONLINE_APPLICATION,
     * and sends a final SMS with the application number.
     *
     * @param formData incoming aggregated DTOs
     * @param tempId   optional draft TEMP_ID from ONLINE_APPLICATION
     */
    public String saveFullApplication(FormDataDto formData, String tempId) {
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

        // Backend-owned fields (as requested)
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

        // Contact info from ApplicationDTO
        ApplicationDTO aDto = formData.getApplicationDto();
        application.setContactIdNo(aDto.getContactIdNo());
        application.setContactName(aDto.getContactName());
        application.setContactAddress(aDto.getContactAddress());
        application.setContactTelephone(aDto.getContactTelephone());
        application.setContactEmail(aDto.getContactEmail());
        application.setContactMobile(aDto.getContactMobile());

        applicationRepository.save(application);

        // ===== WIRING_LAND_DETAIL =====
        logger.debug("DEBUG: Before saving wiring details for applicationId: {}", applicationId);

        WiringLandDetailDto wiringDto = formData.getWiringLandDetailDto();
        if (wiringDto == null) {
            logger.error("DEBUG: WiringLandDetailDto is NULL for applicationId: {}", applicationId);
            throw new IllegalArgumentException("WiringLandDetailDto cannot be null");
        }

        logger.debug("DEBUG: WiringLandDetailDto data - applicationId: {}, deptId: {}",
                wiringDto.getApplicationId(), wiringDto.getDeptId());

        try {
            saveWiringDetail(wiringDto);
            logger.info("DEBUG: Successfully saved wiring details for applicationId: {}", applicationId);
        } catch (Exception e) {
            logger.error("DEBUG: ERROR saving wiring details for applicationId {}: {}",
                    applicationId, e.getMessage(), e);
            throw new RuntimeException("Failed to save wiring details: " + e.getMessage(), e);
        }

        // ===== APPLICATION_REFERENCE =====
        ApplicationReference appRef = new ApplicationReference();
        ApplicationReferencePK appRefPK = new ApplicationReferencePK();
        appRefPK.setApplicationId(application.getId().getApplicationId());
        appRefPK.setDeptId(application.getId().getDeptId());
        appRef.setId(appRefPK);
        appRef.setApplicationNo(application.getApplicationNo());
        appRef.setIdNo(formData.getApplicantDto().getIdNo());
        appRef.setStatus(application.getStatus()); // "N"
        appRef.setPostedBy(application.getPreparedBy());
        appRef.setPostedDate(LocalDate.now());
        appRef.setPostedTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        applicationReferenceRepository.save(appRef);

        // ===== DASHBOARD snapshot =====
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
        // We keep a snapshot; phase might be null on creation → default false
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
        dashboard.setStatus(application.getStatus()); // "N"
        dashboard.setStatusChangedDate(new Date());
        dashboard.setStatusChangedBy("online");
        dashboard.setStatusChangedReason("Submitted online");
        dashboard.setOriginatedBy("WEB");
        dashboard.setOnlineAppNo(application.getApplicationNo());

        dashboardRepository.save(dashboard);

        // ===== Update ONLINE_APPLICATION draft (if we have tempId) =====
        updateOnlineDraft(tempId, applicationNo);

        // ===== Send final SMS (applicationNo) =====
        // Prefer applicant mobile; fall back to contact mobile
        String mobileForSms =
                (formData.getApplicantDto() != null
                        && formData.getApplicantDto().getMobileNo() != null
                        && !formData.getApplicantDto().getMobileNo().isBlank())
                        ? formData.getApplicantDto().getMobileNo()
                        : formData.getApplicationDto().getContactMobile();

        try {
            smsService.sendApplicationNoSms(
                    mobileForSms,
                    applicationNo,
                    deptId
            );
        } catch (Exception ex) {
            // Do not break the flow if SMS fails
            ex.printStackTrace();
        }

        return applicationNo;
    }

    /** Backward-compatible overload (no online draft update). */
    public String saveFullApplication(FormDataDto formData) {
        return saveFullApplication(formData, null);
    }

    // ===== Helpers =====

    private void updateOnlineDraft(String tempId, String applicationNo) {
        if (tempId == null || tempId.isBlank()) return;
        onlineApplicationRepository.findById(tempId).ifPresent(oa -> {
            oa.setApplicationNo(applicationNo);
            oa.setStatus("C"); // Completed
            onlineApplicationRepository.save(oa);
        });
    }

    private static String nullSafe(String v) {
        return v == null ? "" : v.trim();
    }

    // ===== Sequences / IDs =====

    @SuppressWarnings("unchecked")
    public synchronized String getNextAppId(String deptId, String webAppName) {

        String like = deptId + "/ANC/%";

        String sql =
                "SELECT application_id " +
                        "FROM (" +
                        "  SELECT application_id " +
                        "  FROM applications " +
                        "  WHERE application_id LIKE :like " +
                        "  ORDER BY application_id DESC" +
                        ") WHERE ROWNUM = 1";

        List<String> list = entityManager
                .createNativeQuery(sql)
                .setParameter("like", like)
                .getResultList();

        int nextSeq = 1;

        if (!list.isEmpty()) {
            String lastId = list.get(0);
            String lastSeq = lastId.substring(lastId.lastIndexOf("/") + 1);
            nextSeq = Integer.parseInt(lastSeq) + 1;
        }

        return String.format("%04d", nextSeq);
    }


    @SuppressWarnings("unchecked")
    public synchronized String getNextApplicationNo(String deptId, String webAppName) {

        String like = deptId + "/ENC/%";

        String sql =
                "SELECT application_no " +
                        "FROM (" +
                        "  SELECT application_no " +
                        "  FROM applications " +
                        "  WHERE application_no LIKE :like " +
                        "  ORDER BY application_no DESC" +
                        ") WHERE ROWNUM = 1";

        List<String> list = entityManager
                .createNativeQuery(sql)
                .setParameter("like", like)
                .getResultList();

        int nextSeq = 1;

        if (!list.isEmpty()) {
            String lastNo = list.get(0);
            String lastSeq = lastNo.substring(lastNo.lastIndexOf("/") + 1);
            nextSeq = Integer.parseInt(lastSeq) + 1;
        }

        return String.format("%04d", nextSeq);
    }

    public String createNewApplicationId(String deptId) {
        return generateApplicationId(deptId);
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

    // ===== Save helpers =====

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

    private Applicant convertApplicantDTOtoEntity(ApplicantDTO applicantDto) {
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

        // Backend-owned audit for applicant as well
        applicant.setAddUser("online");
        applicant.setAddDate(new Date());
        applicant.setAddTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        applicant.setUpdUser(null);
        applicant.setUpdDate(null);
        applicant.setUpdTime(null);

        return applicant;
    }

    public Applicant createApplicant(ApplicantDTO applicantDto) {
        Applicant applicant = convertApplicantDTOtoEntity(applicantDto);
        return applicantRepository.save(applicant);
    }

    /** Optional: if you call it elsewhere for partial save of contact person only. */
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
        application.setUpdUser(null);
        application.setUpdDate(null);
        application.setUpdTime(null);

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
