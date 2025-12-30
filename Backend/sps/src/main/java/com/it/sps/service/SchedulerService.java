//package com.it.sps.service;
//
//import com.it.sps.dto.SchedulerApplicationDto;
//import com.it.sps.entity.Applicant;
//import com.it.sps.entity.Application;
//import com.it.sps.entity.WiringLandDetail;
//import com.it.sps.repository.ApplicantRepository;
//import com.it.sps.repository.ApplicationRepository;
//import com.it.sps.repository.WiringLandDetailRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class SchedulerService {
//
//    @Autowired
//    private ApplicationRepository applicationRepository;
//
//    @Autowired
//    private WiringLandDetailRepository wiringLandDetailRepository;
//
//    @Autowired
//    private ApplicantRepository applicantRepository;
//
//    public List<SchedulerApplicationDto> getSchedulerApplications() {
//        List<Application> applications = applicationRepository.findAllApplications();
//        List<SchedulerApplicationDto> list = new ArrayList<>();
//
//        for (Application app : applications) {
//            SchedulerApplicationDto dto = new SchedulerApplicationDto();
//            String appId = app.getId().getApplicationId();
//            String deptId = app.getId().getDeptId();
//
//            dto.setApplicationId(appId);
//            dto.setDeptId(deptId);
//
//            //  Use the applicant from the Application entity directly
//            Applicant applicant = app.getApplicant();
//            if (applicant != null) {
//                dto.setApplicantName(applicant.getFullName());
//            }
//
//            //  Contact number - first from Application, fallback to Applicant
//            String contact = app.getContactMobile();
//            if ((contact == null || contact.isEmpty()) && applicant != null) {
//                contact = applicant.getMobileNo();
//            }
//            dto.setContactNumber(contact);
//
//            //  WiringLandDetail for address
//            WiringLandDetail detail = wiringLandDetailRepository.findByIdApplicationIdAndIdDeptId(appId, deptId);
//            if (detail != null) {
//                String address = detail.getServiceStreetAddress() + ", " +
//                        detail.getServiceSuburb() + ", " +
//                        detail.getServiceCity();
//                dto.setServiceAddress(address);
//            } else {
//                dto.setServiceAddress("N/A");
//            }
//
//            list.add(dto);
//        }
//
//        return list;
//    }
//}




//$$$$$$$$department id and application type

//package com.it.sps.service;
//
//import java.util.List;
//import java.util.ArrayList;
//
//import com.it.sps.dto.SchedulerApplicationDto;
//import com.it.sps.repository.ApplicationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SchedulerService {
//
//    @Autowired
//    private ApplicationRepository applicationRepository;
//
//    public List<SchedulerApplicationDto> getSchedulerApplicationsByDeptAndType(String deptId, String applicationType) {
//        return applicationRepository.findApplicationsByDeptAndType(deptId, applicationType);
//    }
//
//}


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
import com.it.sps.repository.SpestedyRepository;

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

    @Autowired
    private SpestedyRepository spestedyRepository;


    /**
     * Fetch all application details filtered by deptId and applicationType
     */
    public List<SchedulerApplicationDto> getSchedulerApplicationsByDeptAndType(String deptId, String applicationType) {

        // 1️⃣ Fetch all applications for the dept + type
        List<Application> applications = applicationRepository.findByDeptIdAndType(deptId, applicationType);

        // 2️⃣ Fetch all reference_no from Spestedy for the same dept
        List<String> scheduledAppIds = spestedyRepository.findAppointmentsByDept(deptId)
                .stream()
                .map(row -> row[2].toString()) // index 2 is reference_no
                .toList();

        List<SchedulerApplicationDto> list = new ArrayList<>();

        // 3️⃣ Loop and only add apps that are NOT scheduled
        for (Application app : applications) {
            String appId = app.getId().getApplicationId();
            if (scheduledAppIds.contains(appId)) continue; // skip scheduled ones

            SchedulerApplicationDto dto = new SchedulerApplicationDto();
            dto.setApplicationId(appId);
            dto.setDeptId(app.getId().getDeptId());

            Applicant applicant = app.getApplicant();
            if (applicant != null) {
                dto.setApplicantName(applicant.getFullName());
                dto.setContactNumber(applicant.getMobileNo());
            } else {
                dto.setApplicantName("N/A");
                dto.setContactNumber(app.getContactMobile());
            }

            WiringLandDetail detail = wiringLandDetailRepository.findByIdApplicationIdAndIdDeptId(appId, app.getId().getDeptId());
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
