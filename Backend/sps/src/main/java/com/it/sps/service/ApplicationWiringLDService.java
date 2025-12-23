package com.it.sps.service;


import com.it.sps.dto.ApplicationDTO;
import com.it.sps.dto.FormDataDto;
import com.it.sps.dto.WiringLandDetailDto;
import com.it.sps.dto.ApplicantDTO;
import com.it.sps.dto.ApplicationReferenceDto;
import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationReference;
import com.it.sps.entity.ApplicationPK;
import com.it.sps.entity.WiringLandDetail;
import com.it.sps.entity.WiringLandDetailPK;
import com.it.sps.entity.ApplicationReferencePK;
import com.it.sps.repository.*;
import com.it.sps.repository.ApplicantRepository;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.WiringLandDetailRepository;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Query;
import jakarta.persistence.EntityManager;

@Service
@Transactional
public class ApplicationWiringLDService {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationRepository applicationRepository;
    private final WiringLandDetailRepository wiringLandDetailRepository;
    private final ApplicationReferenceRepository applicationReferenceRepository;

//    @Autowired
//    private ApplicationReferenceRepository appRefRepository;

    private final ApplicantRepository applicantRepository;

    public ApplicationWiringLDService(
            ApplicationRepository applicationRepository,
            WiringLandDetailRepository wiringLandDetailRepository,
            ApplicantRepository applicantRepository,
            ApplicationReferenceRepository applicationReferenceRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.wiringLandDetailRepository = wiringLandDetailRepository;
        this.applicantRepository = applicantRepository;
        this.applicationReferenceRepository = applicationReferenceRepository;
    }


    public String saveFullApplication(FormDataDto formData) {
        String applicationId = generateApplicationId(formData.getApplicationDto().getDeptId());
        String applicationNo = generateApplicationNo(formData.getApplicationDto().getDeptId());

        formData.getApplicationDto().setApplicationId(applicationId);
        formData.getApplicationDto().setApplicationNo(applicationNo);
        formData.getWiringLandDetailDto().setApplicationId(applicationId);
        formData.getWiringLandDetailDto().setDeptId(formData.getApplicationDto().getDeptId());

        Applicant applicant = createApplicant(formData.getApplicantDto());

        // Save Application entity
        Application application = new Application();
        ApplicationPK applicationPK = new ApplicationPK();
        applicationPK.setApplicationId(applicationId);
        applicationPK.setDeptId(formData.getApplicationDto().getDeptId());
        application.setId(applicationPK);
        application.setApplicationType("NW");
        application.setApplicationNo(applicationNo);
        application.setSubmitDate(new Date());
        application.setStatus("N");
        application.setPreparedBy("ONLINE");
        application.setApplicant(applicant);
        application.setContactIdNo(formData.getApplicationDto().getContactIdNo());
        application.setContactName(formData.getApplicationDto().getContactName());
        application.setContactAddress(formData.getApplicationDto().getContactAddress());
        application.setContactTelephone(formData.getApplicationDto().getContactTelephone());
        application.setContactEmail(formData.getApplicationDto().getContactEmail());
        application.setContactMobile(formData.getApplicationDto().getContactMobile());
        application.setUpdUser("system");
        application.setUpdDate(new Date());
        application.setUpdTime(new java.text.SimpleDateFormat("HH:mm:ss").format(new Date()));
        applicationRepository.save(application);

        // Save WiringLandDetail entity
        saveWiringDetail(formData.getWiringLandDetailDto());

        // Save ApplicationReference entity using Application fields
        ApplicationReference applicationReference = new ApplicationReference();
        ApplicationReferencePK applicationReferencePK = new ApplicationReferencePK();
        applicationReferencePK.setApplicationId(application.getId().getApplicationId());
        applicationReferencePK.setDeptId(application.getId().getDeptId());
        applicationReference.setId(applicationReferencePK);
        applicationReference.setApplicationNo(application.getApplicationNo());
        applicationReference.setIdNo(formData.getApplicantDto().getIdNo());
        applicationReference.setStatus(application.getStatus());
        applicationReference.setPostedBy(application.getPreparedBy());
        applicationReference.setPostedDate(LocalDate.now());
        applicationReference.setPostedTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        applicationReferenceRepository.save(applicationReference);

        System.out.println("ContactIdNo: " + formData.getApplicationDto().getIdNo());

        return applicationNo;
    }

    @SuppressWarnings("unchecked")
    public String getNextAppId(String appIdPrefix, String webAppName) {
        //getEntityManager(webAppName);
        String sequence=null;
        String like=appIdPrefix+"%";
        String strQuery="select a.id.applicationId from Application a " +
                "where a.id.applicationId like :like ORDER BY 1 DESC";
        Query query=entityManager.createQuery(strQuery);//SELECT MIS.TEST4_SEQ.NEXTVAL() FROM DUAL
        query.setParameter("like", like);
        List<String> list=query.getResultList();
        if (list.size()!=0){
            sequence=query.getResultList().get(0).toString().trim();
            sequence=sequence.substring(14);//total 18 chars  year 12
            Integer i=Integer.parseInt(sequence)+1;
            sequence=i.toString();

        }else{
            sequence="0001";

        }
        if(sequence.length()==1)
            return "000"+sequence;
        else if (sequence.length()==2)
            return "00"+sequence;
        else if (sequence.length()==3)
            return "0"+sequence;
        else return sequence;
    }
    @SuppressWarnings("unchecked")
    public String getNextApplicationNo(String applicationNoPrefix, String webAppName) {
        //getEntityManager(webAppName);
        String sequence=null;
        String like=applicationNoPrefix+"%";
        //String strQuery="select APPLICATION_NO from APPLICATION_REFERENCE where APPLICATION_NO like '"+like+"' ORDER BY 1 DESC";
        String strQuery="select APPLICATION_NO from APPLICATIONS where APPLICATION_NO like '"+like+"' ORDER BY 1 DESC";

        Query query=entityManager.createNativeQuery(strQuery);//SELECT MIS.TEST4_SEQ.NEXTVAL() FROM DUAL
        List<String> list=query.getResultList();
        if (list.size()!=0){
            sequence=query.getResultList().get(0).toString().trim();
            //*System.out.println(sequence);
            //sequence=sequence.substring(16);//total 20
            sequence=sequence.substring(14);//total 18
            Integer i=Integer.parseInt(sequence)+1;
            sequence=i.toString();
            System.out.println(sequence);
        }else{
            sequence="0001";
            System.out.println(sequence);
        }
        if(sequence.length()==1)
            return "000"+sequence;
        else if (sequence.length()==2)
            return "00"+sequence;
        else if (sequence.length()==3)
            return "0"+sequence;
        else return sequence;
    }

    public String createNewApplicationId(String deptId) {
        return generateApplicationId(deptId);
    }

    //genarate the applicationId
    private String generateApplicationId(String deptId) {
        String sequence = getNextAppId(deptId,"app");
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s/ANC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
    }

    //genarate the application No
    private String generateApplicationNo(String deptId) {
        String sequence = getNextApplicationNo(deptId, "app"); // Separate sequence method
        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
        return String.format("%s/ENC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
    }


    public void saveContactPersonDetail(ApplicationDTO applicationDto, Applicant applicant) {
        Application application = new Application();
        ApplicationPK applicationPK = new ApplicationPK();

        applicationPK.setApplicationId(applicationDto.getApplicationId());
        applicationPK.setDeptId(applicationDto.getDeptId());
        application.setApplicationType("NW");
        application.setApplicationNo(applicationDto.getApplicationNo());
        application.setSubmitDate(new Date());
        application.setStatus("N");
        application.setPreparedBy("ONLINE");
        application.setApplicant(applicant);

        application.setId(applicationPK);
        application.setContactIdNo(applicationDto.getContactIdNo());
        application.setContactName(applicationDto.getContactName());
        application.setContactAddress(applicationDto.getContactAddress());
        application.setContactTelephone(applicationDto.getContactTelephone());
        application.setContactEmail(applicationDto.getContactEmail());
        application.setContactMobile(applicationDto.getContactMobile());

        applicationRepository.save(application);

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
        applicant.setUpdUser("system");
        applicant.setUpdDate(new Date());
        applicant.setUpdTime(new java.text.SimpleDateFormat("HH:mm:ss").format(new Date()));

        return applicant;

    }
    public Applicant createApplicant(ApplicantDTO applicantDto) {

        Applicant applicant = convertApplicantDTOtoEntity(applicantDto);
        return applicantRepository.save(applicant);

    }
//
//@PersistenceContext
//private EntityManager entityManager;
//    @Autowired
//    private ApplicationRepository applicationRepository;
//    @Autowired
//    private WiringLandDetailRepository wiringLandDetailRepository;
//
////    @Autowired
////    private ApplicationReferenceRepository appRefRepository;
//
//    @Autowired
//    private ApplicantRepository applicantRepository;
//    @Autowired
//    private ApplicationReferenceRepository applicationReferenceRepository;
//
//    public String saveFullApplication(FormDataDto formData) {
//        String applicationId = generateApplicationId(formData.getApplicationDto().getDeptId(), "ANC");
//        String applicationNo = generateApplicationNo(formData.getApplicationDto().getDeptId());
//
//        formData.getApplicationDto().setApplicationId(applicationId);
//        formData.getWiringLandDetailDto().setApplicationId(applicationId);
//        formData.getWiringLandDetailDto().setDeptId(formData.getApplicationDto().getDeptId());
//
//        saveContactPersonDetail(formData.getApplicationDto());
//        saveWiringDetail(formData.getWiringLandDetailDto());
//        createApplicant(formData.getApplicantDto());
//        applicationReference(formData.getApplicationReferenceDto());
//
//        return applicationNo;
//    }
//
//    @SuppressWarnings("unchecked")
//    public String getNextAppId(String appIdPrefix, String webAppName) {
//        //getEntityManager(webAppName);
//        String sequence=null;
//        String like=appIdPrefix+"%";
//        String strQuery="select a.id.applicationId from Application a " +
//                "where a.id.applicationId like :like ORDER BY 1 DESC";
//        Query query=entityManager.createQuery(strQuery);//SELECT MIS.TEST4_SEQ.NEXTVAL() FROM DUAL
//        query.setParameter("like", like);
//        List<String> list=query.getResultList();
//        if (list.size()!=0){
//            sequence=query.getResultList().get(0).toString().trim();
//            sequence=sequence.substring(14);//total 18 chars  year 12
//            Integer i=Integer.parseInt(sequence)+1;
//            sequence=i.toString();
//
//        }else{
//            sequence="0001";
//
//        }
//        if(sequence.length()==1)
//            return "000"+sequence;
//        else if (sequence.length()==2)
//            return "00"+sequence;
//        else if (sequence.length()==3)
//            return "0"+sequence;
//        else return sequence;
//    }
//
//    @SuppressWarnings("unchecked")
//    public String getNextApplicationNo(String applicationNoPrefix, String webAppName) {
//        //getEntityManager(webAppName);
//        String sequence=null;
//        String like=applicationNoPrefix+"%";
//        //String strQuery="select APPLICATION_NO from APPLICATION_REFERENCE where APPLICATION_NO like '"+like+"' ORDER BY 1 DESC";
//        String strQuery="select APPLICATION_NO from APPLICATIONS where APPLICATION_NO like '"+like+"' ORDER BY 1 DESC";
//
//        Query query=entityManager.createNativeQuery(strQuery);//SELECT MIS.TEST4_SEQ.NEXTVAL() FROM DUAL
//        List<String> list=query.getResultList();
//        if (list.size()!=0){
//            sequence=query.getResultList().get(0).toString().trim();
//            //*System.out.println(sequence);
//            //sequence=sequence.substring(16);//total 20
//            sequence=sequence.substring(14);//total 18
//            Integer i=Integer.parseInt(sequence)+1;
//            sequence=i.toString();
//            System.out.println(sequence);
//        }else{
//            sequence="0001";
//            System.out.println(sequence);
//        }
//        if(sequence.length()==1)
//            return "000"+sequence;
//        else if (sequence.length()==2)
//            return "00"+sequence;
//        else if (sequence.length()==3)
//            return "0"+sequence;
//        else return sequence;
//    }
//
//    private String generateApplicationId(String deptId, String prefix) {
//        String sequence = getNextAppId(deptId,"app");
//        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
//        return String.format("%s/%s/%s/%04d", deptId, prefix, yearSuffix, Integer.parseInt(sequence));
//    }
//
//    private String generateApplicationNo(String deptId) {
//        String sequence = getNextApplicationNo(deptId, "app"); // Separate sequence method
//        String yearSuffix = String.valueOf(Year.now().getValue()).substring(2);
//        return String.format("%s/ENC/%s/%04d", deptId, yearSuffix, Integer.parseInt(sequence));
//    }
//
//    public void saveContactPersonDetail(ApplicationDto applicationDto) {
//        Application application = new Application();
//        ApplicationPK applicationPK = new ApplicationPK();
//
//        applicationPK.setApplicationId(applicationDto.getApplicationId());
//        applicationPK.setDeptId(applicationDto.getDeptId());
//
//        application.setId(applicationPK);
//        application.setContactIdNo(applicationDto.getContactIdNo());
//        application.setContactName(applicationDto.getContactName());
//        application.setContactAddress(applicationDto.getContactAddress());
//        application.setContactTelephone(applicationDto.getContactTelephone());
//        application.setContactEmail(applicationDto.getContactEmail());
//        application.setContactMobile(applicationDto.getContactMobile());
//
//        applicationRepository.save(application);
//
//    }
//
//    public void saveWiringDetail(WiringLandDetailDto wiringLandDetailDto) {
//        WiringLandDetail wiringLandDetail = new WiringLandDetail();
//        WiringLandDetailPK wiringLandDetailPK = new WiringLandDetailPK();
//
//        wiringLandDetailPK.setApplicationId(wiringLandDetailDto.getApplicationId());
//        wiringLandDetailPK.setDeptId(wiringLandDetailDto.getDeptId());
//
//        wiringLandDetail.setId(wiringLandDetailPK);
//        wiringLandDetail.setServiceStreetAddress(wiringLandDetailDto.getServiceStreetAddress());
//        wiringLandDetail.setServiceCity(wiringLandDetailDto.getServiceCity());
//        wiringLandDetail.setServiceSuburb(wiringLandDetailDto.getServiceSuburb());
//        wiringLandDetail.setServicePostalCode(wiringLandDetailDto.getServicePostalCode());
//        wiringLandDetail.setAssessmentNo(wiringLandDetailDto.getAssessmentNo());
//        wiringLandDetail.setNeighboursAccNo(wiringLandDetailDto.getNeighboursAccNo());
//        wiringLandDetail.setOwnership(wiringLandDetailDto.getOwnership());
//
//        wiringLandDetail.setMetalCrusher(wiringLandDetailDto.getMetalCrusher());
//        wiringLandDetail.setSawMills(wiringLandDetailDto.getSawMills());
//        wiringLandDetail.setWeldingPlant(wiringLandDetailDto.getWeldingPlant());
//        wiringLandDetail.setPhase(wiringLandDetailDto.getPhase());
//        wiringLandDetail.setCustomerCategory(wiringLandDetailDto.getCustomerCategory());
//        wiringLandDetail.setTariffCatCode(wiringLandDetailDto.getTariffCatCode());
//        wiringLandDetail.setTariffCode(wiringLandDetailDto.getTariffCode());
//        wiringLandDetail.setConnectionType(wiringLandDetailDto.getConnectionType());
//        wiringLandDetail.setCustomerType(wiringLandDetailDto.getCustomerType());
//
//        wiringLandDetailRepository.save(wiringLandDetail);
//    }
//
//    private Applicant convertApplicantDTOtoEntity(ApplicantDto applicantDto) {
//
//        Applicant applicant = new Applicant();
//        applicant.setIdNo(applicantDto.getIdNo());
//        applicant.setIdType(applicantDto.getIdType());
//        applicant.setPersonalCorporate(applicantDto.getPersonalCorporate());
//        applicant.setFirstName(applicantDto.getFirstName());
//        applicant.setLastName(applicantDto.getLastName());
//        applicant.setFullName(applicantDto.getFullName());
//        applicant.setTelephoneNo(applicantDto.getTelephoneNo());
//        applicant.setEmail(applicantDto.getEmail());
//        applicant.setMobileNo(applicantDto.getMobileNo());
//        applicant.setCity(applicantDto.getCity());
//        applicant.setSuburb(applicantDto.getSuburb());
//        applicant.setStreetAddress(applicantDto.getStreetAddress());
//        applicant.setPostalCode(applicantDto.getPostalCode());
//        applicant.setPreferredLanguage(applicantDto.getPreferredLanguage());
//
//        return applicant;
//
//    }
//    public Applicant createApplicant(ApplicantDto applicantDto) {
//
//        Applicant applicant = convertApplicantDTOtoEntity(applicantDto);
//        return applicantRepository.save(applicant);
//
//    }
//
//    public void applicationReference(ApplicationReferenceDto applicationReferenceDto) {
//        ApplicationReference applicationReference = new ApplicationReference();
//        ApplicationReferencePK applicationReferencePK = new ApplicationReferencePK();
//
//        applicationReferencePK.setApplicationId(applicationReferenceDto.getApplicationId());
//        applicationReferencePK.setDeptId(applicationReferenceDto.getDeptId());
//
//        applicationReference.setId(applicationReferencePK);
//        applicationReference.setApplicationNo(applicationReferenceDto.getApplicationNo());
//        applicationReference.setIdNo(applicationReferenceDto.getIdNo());
//        applicationReference.setStatus("N");
//        applicationReference.setPostedBy("Online_User");
//        applicationReference.setPostedDate(LocalDate.now());
//        applicationReference.setPostedTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//
//        applicationReferenceRepository.save(applicationReference);
//    }




}