package com.it.sps.controller;

import com.it.sps.dto.ApplicationFormRequestDTO;
import com.it.sps.service.ApplicationWiringLDBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/api/newapplication")
public class NewApplicationController {

    @Autowired
    private ApplicationWiringLDBackService applicationWiringLDBackService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitApplication(
            @RequestPart("formData") ApplicationFormRequestDTO formData,
            @RequestPart(value = "idCopy", required = false) MultipartFile idCopy,
            @RequestPart(value = "ownershipCertificate", required = false) MultipartFile ownershipCertificate,
            @RequestPart(value = "gramaNiladhariCertificate", required = false) MultipartFile gramaNiladhariCertificate,
            @RequestPart(value = "threephChartedEngineerCertificate", required = false) MultipartFile engineerCertificate
    ) {
        try {
            // FIX: Pass ALL 5 arguments to the service
            String applicationNo = applicationWiringLDBackService.submitFullApplication(
                    formData,
                    idCopy,
                    ownershipCertificate,
                    gramaNiladhariCertificate,
                    engineerCertificate
            );

            return ResponseEntity.ok().body(Map.of(
                    "applicationNo", applicationNo,
                    "message", "Application submitted successfully"
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateApplication(

            @RequestParam("refNo") String refNo,

            @RequestPart("formData") ApplicationFormRequestDTO formData,
            @RequestPart(value = "idCopy", required = false) MultipartFile idCopy,
            @RequestPart(value = "ownershipCertificate", required = false) MultipartFile ownershipCertificate,
            @RequestPart(value = "gramaNiladhariCertificate", required = false) MultipartFile gramaNiladhariCertificate,
            @RequestPart(value = "threephChartedEngineerCertificate", required = false) MultipartFile threephChartedEngineerCertificate
    ) {
        try {
            System.out.println("Update request received for RefNo: " + refNo);

            applicationWiringLDBackService.updateExistingApplication(
                    refNo,
                    formData,
                    idCopy,
                    ownershipCertificate,
                    gramaNiladhariCertificate,
                    threephChartedEngineerCertificate
            );

            return ResponseEntity.ok().body(Map.of(
                    "status", "success",
                    "message", "Application updated successfully"
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Update failed: " + e.getMessage()));
        }
    }
}