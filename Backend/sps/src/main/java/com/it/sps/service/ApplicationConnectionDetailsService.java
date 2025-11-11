package com.it.sps.service;

import com.it.sps.dto.ApplicationConnectionDetailsDto;
import com.it.sps.dto.ApplicationDropdownDto;
import com.it.sps.entity.Application;
import com.it.sps.entity.WiringLandDetail;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.WiringLandDetailRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationConnectionDetailsService {

    private final ApplicationRepository applicationRepository;
    private final WiringLandDetailRepository wiringLandDetailRepository;

    public ApplicationConnectionDetailsService(ApplicationRepository applicationRepository,
                                               WiringLandDetailRepository wiringLandDetailRepository) {
        this.applicationRepository = applicationRepository;
        this.wiringLandDetailRepository = wiringLandDetailRepository;
    }

    // ✅ Get list of all Application Numbers
//    public List<String> getAllApplicationNumbers() {
//        return applicationRepository.findAllApplications()
//                .stream()
//                .map(Application::getApplicationNo)
//                .collect(Collectors.toList());
//    }
    public List<ApplicationDropdownDto> getAllApplicationsWithDept() {
        List<Application> apps = applicationRepository.findAllApplications();
        if (apps == null) {
            System.out.println("DEBUG: No applications found!");
            return List.of();
        }
        return apps.stream()
                .map(app -> {
                    System.out.println("DEBUG: Processing applicationNo=" + app.getApplicationNo());
                    String applicationId = app.getApplicationNo().replace("/ENC/", "/ANC/");
                    WiringLandDetail wiring = wiringLandDetailRepository.findFirstByApplicationIdNative(applicationId);
                    if (wiring == null) {
                        System.out.println("DEBUG: No wiring found for applicationId=" + applicationId);
                    } else if (wiring.getId() == null) {
                        System.out.println("DEBUG: wiring.getId() is null for applicationId=" + applicationId);
                    }
                    String deptId = wiring != null && wiring.getId() != null ? wiring.getId().getDeptId() : null;
                    return new ApplicationDropdownDto(app.getApplicationNo(), deptId);
                })
                .collect(Collectors.toList());
    }



    // ✅ Get details of a single application
    public ApplicationConnectionDetailsDto getApplicationDetails(String applicationNo, String deptId) {
        Application app = applicationRepository.findAllWithApplicant(applicationNo);
        if (app == null) return null;

        String applicationId = app.getApplicationNo() != null ? app.getApplicationNo().replace("/ENC/", "/ANC/") : null;
        if (applicationId == null) return null;

        WiringLandDetail wiring = wiringLandDetailRepository.findByIdApplicationIdAndIdDeptId(applicationId, deptId);
        if (wiring == null || wiring.getId() == null) return null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String applicantName = "";
        String nationalId = "";
        if (app.getApplicant() != null) {
            applicantName = app.getApplicant().getFirstName() + " " + app.getApplicant().getLastName();
            nationalId = app.getApplicant().getIdNo();
        }

        return new ApplicationConnectionDetailsDto(
                app.getApplicationNo(),
                applicantName,
                app.getSubmitDate() != null ? df.format(app.getSubmitDate()) : "",
                nationalId,
                wiring.getNeighboursAccNo(),
                app.getContactAddress(),
                app.getContactTelephone(),
                wiring.getPhase() != null ? wiring.getPhase().toString() : "",
                wiring.getTariffCatCode(),
                wiring.getConnectionType() != null ? wiring.getConnectionType().toString() : "",
                wiring.getTariffCode()
        );
    }

}
