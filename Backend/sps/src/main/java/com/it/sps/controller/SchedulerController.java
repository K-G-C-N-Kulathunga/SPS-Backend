package com.it.sps.controller;

import com.it.sps.dto.SchedulerApplicationDto;
import com.it.sps.service.SchedulerService;
import com.it.sps.dto.AppointmentRequestDto;
import com.it.sps.dto.AppointmentDto;
import com.it.sps.service.AppointmentService;
import com.it.sps.dto.AppointmentResponseDto;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/applications")
    public ResponseEntity<List<SchedulerApplicationDto>> getSchedulerApplications() {
        return ResponseEntity.ok(schedulerService.getSchedulerApplications());

    }

    @PostMapping("/appointments")
    public ResponseEntity<String> addAppointment(@RequestBody AppointmentRequestDto dto) {
        try {
            appointmentService.createAppointment(dto);
            return ResponseEntity.ok("Appointment saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add appointment: " + e.getMessage());
        }
    }

    @GetMapping("/appointments")
    public ResponseEntity<?> getAllAppointments() {
        try {
            List<AppointmentResponseDto> appointments = appointmentService.getAllAppointments();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            e.printStackTrace();  // See full error in logs
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

//    @PutMapping("/appointments/{appointmentId}/{deptId}")
//    public ResponseEntity<String> updateAppointment(
//            @PathVariable String appointmentId,
//            @PathVariable String deptId,
//            @RequestBody AppointmentRequestDto dto) {
//        try {
//            appointmentService.updateAppointment(appointmentId, deptId, dto);
//            return ResponseEntity.ok("Appointment updated successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to update appointment: " + e.getMessage());
//        }
//    }

    @PutMapping("/appointments/{appointmentId}/{deptId}")
    public ResponseEntity<String> updateAppointment(
            @PathVariable String appointmentId,
            @PathVariable String deptId,
            @RequestBody AppointmentRequestDto dto) {
        try {
            System.out.println("Received update request: " + dto);
            appointmentService.updateAppointment(appointmentId, deptId, dto);
            return ResponseEntity.ok("Appointment updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Appointment not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // logs full error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update appointment: " + e.getMessage());
        }
    }


}
