package com.it.sps.service;

import com.it.sps.dto.SchedulerApplicationDto;
import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.entity.WiringLandDetail;
import com.it.sps.repository.ApplicantRepository;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.WiringLandDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private WiringLandDetailRepository wiringLandDetailRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    public List<SchedulerApplicationDto> getSchedulerApplications() {
        List<Application> applications = applicationRepository.findAllApplications();
        List<SchedulerApplicationDto> list = new ArrayList<>();

        for (Application app : applications) {
            SchedulerApplicationDto dto = new SchedulerApplicationDto();
            String appId = app.getId().getApplicationId();
            String deptId = app.getId().getDeptId();

            dto.setApplicationId(appId);
            dto.setDeptId(deptId);

            //  Use the applicant from the Application entity directly
            Applicant applicant = app.getApplicant();
            if (applicant != null) {
                dto.setApplicantName(applicant.getFullName());
            }

            //  Contact number - first from Application, fallback to Applicant
            String contact = app.getContactMobile();
            if ((contact == null || contact.isEmpty()) && applicant != null) {
                contact = applicant.getMobileNo();
            }
            dto.setContactNumber(contact);

            //  WiringLandDetail for address
            WiringLandDetail detail = wiringLandDetailRepository.findByIdApplicationIdAndIdDeptId(appId, deptId);
            if (detail != null) {
                String address = detail.getServiceStreetAddress() + ", " +
                        detail.getServiceSuburb() + ", " +
                        detail.getServiceCity();
                dto.setServiceAddress(address);
            } else {
                dto.setServiceAddress("N/A");
            }

            list.add(dto);
        }

        return list;
    }
}
