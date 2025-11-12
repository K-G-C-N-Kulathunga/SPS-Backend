package com.it.sps.service;

import com.it.sps.dto.AppointmentResponseDto;
import com.it.sps.dto.ApplicantDTO;
import com.it.sps.dto.AppointmentRequestDto;
import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;
import com.it.sps.entity.Applicant;
import com.it.sps.entity.Spestedy;
import com.it.sps.entity.SpestedyId;
import com.it.sps.entity.WiringLandDetail;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.ApplicantRepository;
import com.it.sps.repository.SpestedyRepository;
import com.it.sps.repository.WiringLandDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private WiringLandDetailRepository wiringLandDetailRepository;

    @Autowired
    private SpestedyRepository spestedyRepository;

    /**
     * Create a new appointment
     */
//    public void createAppointment(AppointmentRequestDto dto) {
//        ApplicationPK pk = new ApplicationPK();
//        pk.setApplicationId(dto.getApplicationId());
//        pk.setDeptId(dto.getDeptId());
//
//        Application application = applicationRepository.findWithApplicantById(pk);
//        if (application == null) {
//            throw new RuntimeException("Application not found");
//        }
//
//        String deptId = pk.getDeptId();
//        Applicant applicant = application.getApplicant();
//        if (applicant == null) {
//            throw new RuntimeException("Applicant entity missing in application");
//        }
//
//        ApplicantDTO applicantDto = applicantRepository.findApplicantDTOByIdNo(applicant.getIdNo());
//        if (applicantDto == null) {
//            throw new RuntimeException("Applicant not found");
//        }
//
//        WiringLandDetail wiring = wiringLandDetailRepository.findByIdApplicationIdAndIdDeptId(
//                dto.getApplicationId(), deptId);
//        if (wiring == null) {
//            throw new RuntimeException("WiringLandDetail not found");
//        }
//
//        Spestedy spestedy = new Spestedy();
//        spestedy.setAppointmentId(UUID.randomUUID().toString().substring(0, 12));
//        spestedy.setDeptId(deptId);
//        spestedy.setAppointmentDate(java.sql.Date.valueOf(LocalDate.parse(dto.getDate())));
//        spestedy.setTimeSession(dto.getSession());
//        spestedy.setAllocatedTo(dto.getInspector());
//        spestedy.setDescription(dto.getDescription());
//        spestedy.setAllocatedBy("SYSTEM");
//        spestedy.setAllocatedDate(new Date());
//        spestedy.setStatus("P");
//        spestedy.setAppointmentType("SITE");
//        spestedy.setReferenceNo(application.getId().getApplicationId());
//        spestedy.setSuburb(applicantDto.getSuburb());
//
//        spestedyRepository.save(spestedy);
//    }

    public void createAppointment(AppointmentRequestDto dto) {
        ApplicationPK pk = new ApplicationPK();
        pk.setApplicationId(dto.getApplicationId());
        pk.setDeptId(dto.getDeptId());

        Application application = applicationRepository.findWithApplicantById(pk);
        if (application == null) {
            throw new RuntimeException("Application not found");
        }

        String deptId = pk.getDeptId();

        // Build prefix = YYYY/MM
        LocalDate now = LocalDate.now();
        String prefix = String.format("%04d/%02d/", now.getYear(), now.getMonthValue());

        // Fetch last appointment ID for this year/month
        String lastId = spestedyRepository.findLatestAppointmentId(prefix);

        int nextSeq = 1;
        if (lastId != null) {
            // Extract sequence part (last 4 digits)
            String[] parts = lastId.split("/");
            nextSeq = Integer.parseInt(parts[2]) + 1;
        }

        String appointmentId = prefix + String.format("%04d", nextSeq);

        // ---- Create entity ----
        Spestedy spestedy = new Spestedy();
        spestedy.setAppointmentId(appointmentId);
        spestedy.setDeptId(deptId);
        spestedy.setAppointmentDate(java.sql.Date.valueOf(LocalDate.parse(dto.getDate())));
        spestedy.setTimeSession(dto.getSession());
        spestedy.setAllocatedTo(dto.getInspector());
        spestedy.setDescription(dto.getDescription());
        spestedy.setAllocatedBy("SYSTEM");
        spestedy.setAllocatedDate(new Date());
        spestedy.setStatus("P");
        spestedy.setAppointmentType("ES.VISIT");
        spestedy.setReferenceNo(application.getId().getApplicationId());

        spestedyRepository.save(spestedy);
    }


    /**
     * Delete appointment by ID
     */
    public void deleteAppointment(String appointmentId, String deptId) {
        SpestedyId id = new SpestedyId(appointmentId, deptId);
        Optional<Spestedy> optional = spestedyRepository.findById(id);
        if (optional.isPresent()) {
            spestedyRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Appointment not found with ID: " + appointmentId + " and Dept: " + deptId);
        }
    }

    /**
     * Fetch all saved appointments (for weekly table)
     */
//    public List<AppointmentResponseDto> getAllAppointments() {
//        return spestedyRepository.findAllAppointmentsJoined();
//    }

//    public List<AppointmentResponseDto> getAllAppointments() {
//        List<Object[]> rows = spestedyRepository.findAllAppointmentsJoined();
//        List<AppointmentResponseDto> result = new ArrayList<>();
//
//        for (Object[] row : rows) {
//            AppointmentResponseDto dto = new AppointmentResponseDto(
//                    row[0] != null ? row[0].toString() : null,
//                    row[1] != null ? row[1].toString() : null,
//                    row[2] != null ? row[2].toString() : null,
//                    row[3] != null ? row[3].toString() : null,
//                    row[4] != null ? row[4].toString() : null,
//                    row[5] != null ? row[5].toString() : null,
//                    row[6] != null ? row[6].toString() : null,
//                    row[7] != null ? row[7].toString() : null,
//                    row[8] != null ? row[8].toString() : null
//            );
//            result.add(dto);
//        }
//
//        return result;
//    }

    public List<AppointmentResponseDto> getAppointmentsByDept(String deptId) {
        List<Object[]> rows = spestedyRepository.findAppointmentsByDept(deptId);
        List<AppointmentResponseDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            AppointmentResponseDto dto = new AppointmentResponseDto(
                    row[0] != null ? row[0].toString() : null,
                    row[1] != null ? row[1].toString() : null,
                    row[2] != null ? row[2].toString() : null,
                    row[3] != null ? row[3].toString() : null,
                    row[4] != null ? row[4].toString() : null,
                    row[5] != null ? row[5].toString() : null,
                    row[6] != null ? row[6].toString() : null,
                    row[7] != null ? row[7].toString() : null,
                    row[8] != null ? row[8].toString() : null
            );
            result.add(dto);
        }

        return result;
    }


    public void updateAppointment(String appointmentId, String deptId, AppointmentRequestDto dto) {
        SpestedyId id = new SpestedyId(appointmentId, deptId);
        Optional<Spestedy> optional = spestedyRepository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Appointment not found");
        }

        Spestedy appointment = optional.get();

        // Only update fields if not null (avoid overwriting with nulls)
        if (dto.getDescription() != null) {
            appointment.setDescription(dto.getDescription());
        }

        if (dto.getStatus() != null) {
            // If DB stores status as single char, map here
            String status = dto.getStatus();
            if (status.equalsIgnoreCase("Cancel Visit")) {
                status = "Cancel Visit"; // adjust as per DB enum
            } else if (status.equalsIgnoreCase("Re visit")) {
                status = "Re visit";
            }
            appointment.setStatus(status);
        }

        try {
            spestedyRepository.save(appointment);
        } catch (Exception e) {
            // Log detailed root cause
            System.err.println("Failed to update appointment:");
            e.printStackTrace();
            throw new RuntimeException("Database update failed: " + e.getMessage(), e);
        }
    }



}


