package com.it.sps.controller;

import com.it.sps.dto.ApplicationConnectionDetailsDto;
import com.it.sps.dto.ApplicationDropdownDto;
import com.it.sps.dto.ServiceEstimateDto;
import com.it.sps.dto.SpdppolmDTO;
import com.it.sps.dto.SpstrutmDTO;
import com.it.sps.dto.SpstaymtDTO;
import com.it.sps.service.ApplicationConnectionDetailsService;
import com.it.sps.service.ServiceEstimateService;
import com.it.sps.service.SpdppolmService;
import com.it.sps.service.SpstrutmService;
import com.it.sps.service.SpstaymtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications/connection-details")
public class ApplicationConnectionDetailsController {

    private final ApplicationConnectionDetailsService service;
    private final ServiceEstimateService serviceEstimateService;
    private final SpdppolmService spdppolmService;
    private final SpstrutmService spstrutmService;
    private final SpstaymtService spstaymtService;

    public ApplicationConnectionDetailsController(ApplicationConnectionDetailsService service,
            ServiceEstimateService serviceEstimateService,
            SpdppolmService spdppolmService,
            SpstrutmService spstrutmService,
            SpstaymtService spstaymtService) {
        this.service = service;
        this.serviceEstimateService = serviceEstimateService;
        this.spdppolmService = spdppolmService;
        this.spstrutmService = spstrutmService;
        this.spstaymtService = spstaymtService;
    }

    // ========================
    // Application Connection Endpoints
    // ========================

    @GetMapping("/all")
    public ResponseEntity<List<ApplicationDropdownDto>> getAllApplicationsWithDept(
            @RequestParam String deptId,
            @RequestParam String applicationType,
            @RequestParam String status) {
        try {
            List<ApplicationDropdownDto> list = service.getApplicationsByDeptTypeStatus(deptId, applicationType,
                    status);
            return ResponseEntity.ok(list != null ? list : List.of());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getApplicationDetails(@RequestParam String applicationNo,
            @RequestParam String deptId) {
        try {
            ApplicationConnectionDetailsDto dto = service.getApplicationDetails(applicationNo, deptId);
            if (dto == null) {
                return ResponseEntity.status(404)
                        .body("No application found for applicationNo=" + applicationNo + " and deptId=" + deptId);
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ========================
    // Service Estimate Endpoints
    // ========================

    @PostMapping("/service-estimate/save")
    public ResponseEntity<ServiceEstimateService.ServiceEstimateResult> saveServiceEstimate(
            @RequestBody ServiceEstimateDto dto) {
        try {
            return ResponseEntity.ok(serviceEstimateService.saveServiceEstimate(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/service-estimate/save-from-frontend")
    public ResponseEntity<?> saveFromFrontend(
            @RequestBody Map<String, Object> frontendData) {
        try {
            return ResponseEntity.ok(serviceEstimateService.saveFromFrontendData(frontendData));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to save service estimate data",
                    "message", e.getMessage()));
        }
    }

    // ========================
    // SPDPPOLM Endpoints
    // ========================

    @GetMapping("/spdppolm")
    public ResponseEntity<List<SpdppolmDTO>> getMatCdByDeptId(@RequestParam String deptId) {
        try {
            List<SpdppolmDTO> list = spdppolmService.getMatCdByDeptId(deptId);
            return ResponseEntity.ok(list != null ? list : List.of());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }

    // ========================
    // SPSTRUTM Endpoints
    // ========================

    @GetMapping("/spstrutm")
    public ResponseEntity<List<SpstrutmDTO>> getMatCdFromSpstrutm() {
        try {
            // Hardcoded DEPT_ID
            String deptId = "452.00";
            List<SpstrutmDTO> list = spstrutmService.getMatCdByDeptId(deptId);
            return ResponseEntity.ok(list != null ? list : List.of());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }

    // ========================
    // SPSTAYMT Endpoints
    // ========================

    @GetMapping("/spstaymt")
    public ResponseEntity<List<SpstaymtDTO>> getMatCdFromSpstaymt(@RequestParam String deptId) {
        try {
            List<SpstaymtDTO> list = spstaymtService.getMatCdByDeptId(deptId);
            return ResponseEntity.ok(list != null ? list : List.of());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }
}
