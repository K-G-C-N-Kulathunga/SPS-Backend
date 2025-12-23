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

    // ✅ Fetch applications filtered by deptId, applicationType, and status
    public List<ApplicationDropdownDto> getApplicationsByDeptTypeStatus(String deptId, String applicationType, String status) {
        List<Application> apps = applicationRepository.findApplicationsByDeptTypeStatus(deptId, applicationType, status);

        return apps.stream()
                .map(app -> {
                    String applicationId = app.getApplicationNo().replace("/ENC/", "/ANC/");
                    WiringLandDetail wiring = wiringLandDetailRepository.findByIdApplicationIdAndIdDeptId(applicationId, deptId);
                    String dept = wiring != null && wiring.getId() != null ? wiring.getId().getDeptId() : null;
                    return new ApplicationDropdownDto(app.getApplicationNo(), dept);
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
