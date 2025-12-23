package com.it.sps.service;

import com.it.sps.dto.AddressDetailsDto;
import com.it.sps.entity.OnlineApplication;
import com.it.sps.repository.OnlineApplicationRepository;
import com.it.sps.service.OnlineApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


import com.it.sps.entity.Gldeptin;
import com.it.sps.repository.GldeptinRepository;

import java.time.Instant;
import java.time.Year;
import java.util.Date;

@Service
public class OnlineApplicationServiceImpl implements OnlineApplicationService {

//    @Autowired
//    private OnlineApplicationRepository onlineApplicationRepository;
//
//
//    @Autowired
//    private GldeptinRepository gldeptinRepository;
//
//
//    @Override
//    public OnlineApplication createApplication(OnlineApplication application) {
//        String tempId = generateTempId(application.getMobile());
//        application.setTempId(tempId);
//        application.setApplicationDate(new Date());
//
//        return onlineApplicationRepository.save(application);
//    }

    private static final Logger log = LoggerFactory.getLogger(OnlineApplicationServiceImpl.class);

    @Autowired
    private OnlineApplicationRepository onlineApplicationRepository;

    @Autowired
    private GldeptinRepository gldeptinRepository;

    @Autowired
    private SmsService smsService; // <-- add this

    @Override
    @Transactional
    public OnlineApplication createApplication(OnlineApplication application) {
        String tempId = generateTempId(application.getMobile());
        application.setTempId(tempId);
        application.setApplicationDate(new Date());

        application.setStatus("N");

        OnlineApplication saved = onlineApplicationRepository.save(application);

        // fire-and-forget SMS
        try {
            smsService.sendTempIdSms(saved.getMobile(), saved.getTempId());
        } catch (Exception e) {
            // already handled inside service, but double safety
            log.warn("Non-fatal: SMS send attempt failed for TEMP_ID={}", saved.getTempId());
        }

        return saved;
    }

    public String generateTempId(String mobile) {
        String year = String.valueOf(Year.now().getValue()).substring(2); // e.g., "25"

        // Get latest TEMP_ID serial for the mobile+year
        String maxSerialStr = onlineApplicationRepository.findMaxSerialByMobileAndYear(mobile, year);

        int nextSerial = (maxSerialStr == null) ? 1 : Integer.parseInt(maxSerialStr) + 1;

        String serialFormatted = String.format("%03d", nextSerial); // 3-digit serial
        return mobile + year + serialFormatted;
    }




    @Override
    public OnlineApplication updateApplication(String tempId, OnlineApplication updatedApplication) {
        OnlineApplication existing = onlineApplicationRepository.findById(tempId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + tempId));

        String mobile = existing.getMobile(); // Keep the same mobile
        copyNonNullProperties(updatedApplication, existing);

        // DO NOT generate and set a new tempId here
        // existing.setTempId(newTempId);

        existing.setMobile(mobile);
        existing.setApplicationDate(new Date());

        return onlineApplicationRepository.save(existing);
    }


    /**
     * Copy non-null properties from source to target
     */
    private void copyNonNullProperties(OnlineApplication source, OnlineApplication target) {
        if (source.getIdNo() != null) target.setIdNo(source.getIdNo());
        if (source.getIdType() != null) target.setIdType(source.getIdType());
        if (source.getFirstName() != null) target.setFirstName(source.getFirstName());
        if (source.getLastName() != null) target.setLastName(source.getLastName());
        if (source.getFullName() != null) target.setFullName(source.getFullName());
        if (source.getStreetAddress() != null) target.setStreetAddress(source.getStreetAddress());
        if (source.getSuburb() != null) target.setSuburb(source.getSuburb());
        if (source.getCity() != null) target.setCity(source.getCity());
        if (source.getPostalCode() != null) target.setPostalCode(source.getPostalCode());
        if (source.getEmail() != null) target.setEmail(source.getEmail());
        if (source.getTelephone() != null) target.setTelephone(source.getTelephone());
        if (source.getContactIdNo() != null) target.setContactIdNo(source.getContactIdNo());
        if (source.getContactName() != null) target.setContactName(source.getContactName());
        if (source.getContactAddress() != null) target.setContactAddress(source.getContactAddress());
        if (source.getContactTelephone() != null) target.setContactTelephone(source.getContactTelephone());
        if (source.getContactMobile() != null) target.setContactMobile(source.getContactMobile());
        if (source.getContactEmail() != null) target.setContactEmail(source.getContactEmail());
        if (source.getServiceStreetAddress() != null) target.setServiceStreetAddress(source.getServiceStreetAddress());
        if (source.getServiceSuburb() != null) target.setServiceSuburb(source.getServiceSuburb());
        if (source.getServiceCity() != null) target.setServiceCity(source.getServiceCity());
        if (source.getServicePostalCode() != null) target.setServicePostalCode(source.getServicePostalCode());
        if (source.getAssessmentNo() != null) target.setAssessmentNo(source.getAssessmentNo());
        if (source.getNeighboursAccNo() != null) target.setNeighboursAccNo(source.getNeighboursAccNo());
        if (source.getDeptId() != null) target.setDeptId(source.getDeptId());
        if (source.getPhase() != null) target.setPhase(source.getPhase());
        if (source.getConnectionType() != null) target.setConnectionType(source.getConnectionType());
        if (source.getTariffCatCode() != null) target.setTariffCatCode(source.getTariffCatCode());
        if (source.getNoOfBulbs() != null) target.setNoOfBulbs(source.getNoOfBulbs());
        if (source.getNoOfFans() != null) target.setNoOfFans(source.getNoOfFans());
        if (source.getNoOfPlugs5a() != null) target.setNoOfPlugs5a(source.getNoOfPlugs5a());
        if (source.getNoOfPlugs15a() != null) target.setNoOfPlugs15a(source.getNoOfPlugs15a());
        if (source.getMotorTotal() != null) target.setMotorTotal(source.getMotorTotal());
        if (source.getWeldingPlant() != null) target.setWeldingPlant(source.getWeldingPlant());
        if (source.getMetalCrusher() != null) target.setMetalCrusher(source.getMetalCrusher());
        if (source.getSawMill() != null) target.setSawMill(source.getSawMill());
        if (source.getIsTransfered() != null) target.setIsTransfered(source.getIsTransfered());
        if (source.getUsageElectricity() != null) target.setUsageElectricity(source.getUsageElectricity());
        if (source.getRequestingTime() != null) target.setRequestingTime(source.getRequestingTime());
        if (source.getBoundaryWall() != null) target.setBoundaryWall(source.getBoundaryWall());
        if (source.getPreAccountNo() != null) target.setPreAccountNo(source.getPreAccountNo());
        if (source.getLongitude() != null) target.setLongitude(source.getLongitude());
        if (source.getLatitude() != null) target.setLatitude(source.getLatitude());
        if (source.getOwnership() != null) target.setOwnership(source.getOwnership());

    }

    public AddressDetailsDto getAddressDetails(String tempId) {
        OnlineApplication application = onlineApplicationRepository.findById(tempId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + tempId));

        // Optionally split streetAddress into components if needed
        String fullStreet = application.getStreetAddress();
        String houseNo = ""; // Extract house number if possible
        String streetName = fullStreet;

        // Example of splitting (only if format is consistent like "No 12, Main Street")
        if (fullStreet != null && fullStreet.contains(",")) {
            String[] parts = fullStreet.split(",", 2);
            houseNo = parts[0].trim();
            streetName = parts[1].trim();
        }

        return new AddressDetailsDto(streetName, houseNo, application.getCity(), application.getPostalCode());
    }

    @Override
    public OnlineApplication getApplicationByTempId(String tempId) {
        return onlineApplicationRepository.findById(tempId).orElse(null);
    }

    public AddressDetailsDto getServiceLocationDetailsWithAreaAndCSC(String tempId) {
        OnlineApplication application = onlineApplicationRepository.findById(tempId)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + tempId));

        String deptId = application.getDeptId();
        String area = "";
        String nearestCSC = "";

        if (deptId != null) {
            Gldeptin dept = gldeptinRepository.findById(deptId).orElse(null);
            if (dept != null) {
                area = dept.getDeptArea();
                nearestCSC = dept.getDeptFullName();
            }
        }

        AddressDetailsDto dto = new AddressDetailsDto(
                application.getServiceStreetAddress(),
                "", // houseNo if available
                application.getServiceCity(),
                application.getServicePostalCode()
        );
        dto.setArea(area);
        dto.setNearestCSC(nearestCSC);
        return dto;
    }

}
