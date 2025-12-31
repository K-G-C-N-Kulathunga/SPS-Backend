package com.it.sps.controller;

import com.it.sps.dto.ApplicationConnectionDetailsDto;
import com.it.sps.dto.ApplicationDropdownDto;
import com.it.sps.dto.ServiceEstimateDto;
import com.it.sps.service.ApplicationConnectionDetailsService;
import com.it.sps.service.ServiceEstimateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications/connection-details")
public class ApplicationConnectionDetailsController {

    private final ApplicationConnectionDetailsService service;
    private final ServiceEstimateService serviceEstimateService;

    public ApplicationConnectionDetailsController(ApplicationConnectionDetailsService service,
                                                  ServiceEstimateService serviceEstimateService) {
        this.service = service;
        this.serviceEstimateService = serviceEstimateService;
    }

    // GET: All application numbers for dropdown
    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDropdownDto>> getAllApplicationsWithDept() {
        try {
            List<ApplicationDropdownDto> list = service.getAllApplicationsWithDept();
            if (list == null || list.isEmpty()) {
                return ResponseEntity.ok(List.of());
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }

    // Simple test
    @GetMapping("/test")
    public String testEndpoint(@RequestParam String deptId) {
        return "It works! deptId=" + deptId;
    }

    // GET: Specific application details
//    @GetMapping("/details")
//    public ResponseEntity<?> getApplicationDetails(@RequestParam String applicationNo,
//                                                   @RequestParam String deptId) {
//        System.out.println("Hit endpoint: applicationNo=" + applicationNo + ", deptId=" + deptId);
//        ApplicationConnectionDetailsDto dto = service.getApplicationDetails(applicationNo, deptId);
//        if (dto == null) {
//            return ResponseEntity.status(404)
//                    .body("No application found for applicationNo=" + applicationNo + " and deptId=" + deptId);
//        }
//        return ResponseEntity.ok(dto);
//    }

    @GetMapping("/details")
    public ResponseEntity<?> getApplicationDetails(@RequestParam String applicationNo,
                                                   @RequestParam String deptId) {
        try {
            System.out.println("Hit endpoint: applicationNo=" + applicationNo + ", deptId=" + deptId);
            ApplicationConnectionDetailsDto dto = service.getApplicationDetails(applicationNo, deptId);
            if (dto == null) {
                return ResponseEntity.status(404)
                        .body("No application found for applicationNo=" + applicationNo + " and deptId=" + deptId);
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace(); // full stack trace
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    // === Service Estimate (migrated from ServiceEstimateController) ===

    @PostMapping("/service-estimate/save")
    public ResponseEntity<ServiceEstimateService.ServiceEstimateResult> saveServiceEstimate(
            @RequestBody ServiceEstimateDto dto) {
        try {
            ServiceEstimateService.ServiceEstimateResult result = serviceEstimateService.saveServiceEstimate(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/service-estimate/save-from-frontend")
    public ResponseEntity<ServiceEstimateService.ServiceEstimateResult> saveFromFrontend(
            @RequestBody Map<String, Object> frontendData) {
        try {
            System.out.println("DEBUG: Unified Controller - Received request with data: " + frontendData);
            ServiceEstimateService.ServiceEstimateResult result =
                    serviceEstimateService.saveFromFrontendData(frontendData);
            System.out.println("DEBUG: Unified Controller - Successfully processed request");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println("DEBUG: Unified Controller - Exception occurred: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}