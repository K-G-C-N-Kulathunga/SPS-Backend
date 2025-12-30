package com.it.sps.service;

import com.it.sps.dto.ApplicationDTO;
import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;
import com.it.sps.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private HttpSession session;

    public Application saveApplication(ApplicationDTO applicationDTO, String sessionUsername) {
        Application application = new Application();
        ApplicationPK id = new ApplicationPK();
        id.setApplicationId(applicationDTO.getApplicationId());
        //id.setDeptId(applicationDTO.getDeptId());
        application.setId(id);
        application.setApplicationNo(applicationDTO.getApplicationNo());
        application.setApplicationType(applicationDTO.getApplicationType());
        application.setApplicationSubType(applicationDTO.getApplicationSubType());
        application.setSubmitDate(applicationDTO.getSubmitDate());
        application.getApplicant().setIdNo(applicationDTO.getIdNo());

        // Use session username for tracking
        id.setDeptId((String) session.getAttribute("deptid"));
        application.setPreparedBy(sessionUsername);
        application.setAddUser(sessionUsername);
        application.setUpdUser(sessionUsername);

        //application.setPreparedBy(applicationDTO.getPreparedBy());
        application.setConfirmedBy(applicationDTO.getConfirmedBy());
        application.setConfirmedDate(applicationDTO.getConfirmedDate());
        application.setConfirmedTime(applicationDTO.getConfirmedTime());
        application.setAllocatedTo(applicationDTO.getAllocatedTo());
        application.setAllocatedBy(applicationDTO.getAllocatedBy());
        application.setAllocatedDate(applicationDTO.getAllocatedDate());
        application.setAllocatedTime(applicationDTO.getAllocatedTime());
        application.setStatus(applicationDTO.getStatus());
        //application.setAddUser(applicationDTO.getAddUser());
        application.setAddDate(applicationDTO.getAddDate());
        application.setAddTime(applicationDTO.getAddTime());
        //application.setUpdUser(applicationDTO.getUpdUser());
        application.setUpdDate(applicationDTO.getUpdDate());
        application.setUpdTime(applicationDTO.getUpdTime());
        application.setDescription(applicationDTO.getDescription());
        application.setFromDate(applicationDTO.getFromDate());
        application.setToDate(applicationDTO.getToDate());
        application.setDuration(new BigDecimal(applicationDTO.getDurationInDays()));
        application.setDurationType(applicationDTO.getDurationType());
        //application.setDuration(applicationDTO.getDuration());
        application.setDisconnectedWithin(new BigDecimal(applicationDTO.getDisconnectedWithin()));
        application.setFinalizedWithin(new BigDecimal(applicationDTO.getFinalizedWithin()));
        application.setIsLoanApp(applicationDTO.getIsLoanApp());
        application.setIsVisitngNeeded(applicationDTO.getIsVisitngNeeded());
        application.setSamurdhiMember(applicationDTO.getSamurdhiMember());
        application.setContactIdNo(applicationDTO.getContactIdNo());
        application.setContactName(applicationDTO.getContactName());
        application.setContactAddress(applicationDTO.getContactAddress());
        application.setContactTelephone(applicationDTO.getContactTelephone());
        application.setContactMobile(applicationDTO.getContactMobile());
        application.setContactEmail(applicationDTO.getContactEmail());
        application.setIsPiv1Needed(applicationDTO.getIsPiv1Needed());
        application.setLinkedWith(applicationDTO.getLinkedWith());
        application.setApplicableStdYear(applicationDTO.getApplicableStdYear());
        application.setIsTariffChange(applicationDTO.getIsTariffChange());
        application.setIsSequenceChange(applicationDTO.getIsSequenceChange());
        application.setExistTariff(applicationDTO.getExistTariff());

        return applicationRepository.save(application);
    }

    public Optional<Application> getApplicationById(String applicationId) {
        return applicationRepository.findByApplicationId(applicationId);
    }

    public Application updateApplication(String applicationId, ApplicationDTO applicationDTO, String sessionUsername) {
    	Optional<Application> optionalApplication = applicationRepository.findByApplicationId(applicationId);

        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();

            // Update only non-null fields
            if (applicationDTO.getApplicationNo() != null) application.setApplicationNo(applicationDTO.getApplicationNo());
            if (applicationDTO.getApplicationType() != null) application.setApplicationType(applicationDTO.getApplicationType());
            if (applicationDTO.getApplicationSubType() != null) application.setApplicationSubType(applicationDTO.getApplicationSubType());
            if (applicationDTO.getSubmitDate() != null) application.setSubmitDate(applicationDTO.getSubmitDate());
            if (applicationDTO.getIdNo() != null) application.getApplicant().setIdNo(applicationDTO.getIdNo());

            // Use session username for tracking
            application.getId().setDeptId((String) session.getAttribute("deptid"));
            application.setPreparedBy(sessionUsername);
            application.setUpdUser(sessionUsername);

            if (applicationDTO.getConfirmedBy() != null) application.setConfirmedBy(applicationDTO.getConfirmedBy());
            if (applicationDTO.getConfirmedDate() != null) application.setConfirmedDate(applicationDTO.getConfirmedDate());
            if (applicationDTO.getConfirmedTime() != null) application.setConfirmedTime(applicationDTO.getConfirmedTime());
            if (applicationDTO.getAllocatedTo() != null) application.setAllocatedTo(applicationDTO.getAllocatedTo());
            if (applicationDTO.getAllocatedBy() != null) application.setAllocatedBy(applicationDTO.getAllocatedBy());
            if (applicationDTO.getAllocatedDate() != null) application.setAllocatedDate(applicationDTO.getAllocatedDate());
            if (applicationDTO.getAllocatedTime() != null) application.setAllocatedTime(applicationDTO.getAllocatedTime());
            if (applicationDTO.getStatus() != null) application.setStatus(applicationDTO.getStatus());

            if (applicationDTO.getUpdDate() != null) application.setUpdDate(applicationDTO.getUpdDate());
            if (applicationDTO.getUpdTime() != null) application.setUpdTime(applicationDTO.getUpdTime());
            if (applicationDTO.getDescription() != null) application.setDescription(applicationDTO.getDescription());
            if (applicationDTO.getFromDate() != null) application.setFromDate(applicationDTO.getFromDate());
            if (applicationDTO.getToDate() != null) application.setToDate(applicationDTO.getToDate());
            if (applicationDTO.getDurationInDays() != null) application.setDurationInDays(new BigDecimal(applicationDTO.getDurationInDays()));
            if (applicationDTO.getDurationType() != null) application.setDurationType(applicationDTO.getDurationType());
            if (applicationDTO.getDuration() != null) application.setDuration(new BigDecimal(applicationDTO.getDuration()));
            if (applicationDTO.getDisconnectedWithin() != null) application.setDisconnectedWithin(new BigDecimal(applicationDTO.getDisconnectedWithin()));
            if (applicationDTO.getFinalizedWithin() != null) application.setFinalizedWithin(new BigDecimal(applicationDTO.getFinalizedWithin()));
            if (applicationDTO.getIsLoanApp() != null) application.setIsLoanApp(applicationDTO.getIsLoanApp());
            if (applicationDTO.getIsVisitngNeeded() != null) application.setIsVisitngNeeded(applicationDTO.getIsVisitngNeeded());
            if (applicationDTO.getSamurdhiMember() != null) application.setSamurdhiMember(applicationDTO.getSamurdhiMember());
            if (applicationDTO.getContactIdNo() != null) application.setContactIdNo(applicationDTO.getContactIdNo());
            if (applicationDTO.getContactName() != null) application.setContactName(applicationDTO.getContactName());
            if (applicationDTO.getContactAddress() != null) application.setContactAddress(applicationDTO.getContactAddress());
            if (applicationDTO.getContactTelephone() != null) application.setContactTelephone(applicationDTO.getContactTelephone());
            if (applicationDTO.getContactMobile() != null) application.setContactMobile(applicationDTO.getContactMobile());
            if (applicationDTO.getContactEmail() != null) application.setContactEmail(applicationDTO.getContactEmail());
            if (applicationDTO.getIsPiv1Needed() != null) application.setIsPiv1Needed(applicationDTO.getIsPiv1Needed());
            if (applicationDTO.getLinkedWith() != null) application.setLinkedWith(applicationDTO.getLinkedWith());
            if (applicationDTO.getApplicableStdYear() != null) application.setApplicableStdYear(applicationDTO.getApplicableStdYear());
            if (applicationDTO.getIsTariffChange() != null) application.setIsTariffChange(applicationDTO.getIsTariffChange());
            if (applicationDTO.getIsSequenceChange() != null) application.setIsSequenceChange(applicationDTO.getIsSequenceChange());
            if (applicationDTO.getExistTariff() != null) application.setExistTariff(applicationDTO.getExistTariff());

            return applicationRepository.save(application);
        } else {
            throw new EntityNotFoundException("Application with ID " + applicationId + " not found.");
        }
    }

    public List<String> getAllApplicationNos() {
        return applicationRepository.findAllApplicationNos();
    }

    public boolean validateApplicationNo(String applicationNo) {
        return applicationRepository.existsByApplicationNo(applicationNo);
    }
}
