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
import com.it.sps.service.SpsetpolService;
import com.it.sps.service.SpsetstuService;
import com.it.sps.service.SpsetstyService;
import com.it.sps.repository.SpsErestRepository;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.dto.SpsErestDto;
import com.it.sps.service.SpsErestService;
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
    private final SpsErestRepository spsErestRepository;
    private final ApplicationRepository applicationRepository;
    private final SpsErestService spsErestService;
    private final SpsetpolService spsetpolService;
    private final SpsetstuService spsetstuService;
    private final SpsetstyService spsetstyService;

    public ApplicationConnectionDetailsController(ApplicationConnectionDetailsService service,
            ServiceEstimateService serviceEstimateService,
            SpdppolmService spdppolmService,
            SpstrutmService spstrutmService,
            SpstaymtService spstaymtService,
            SpsErestRepository spsErestRepository,
            ApplicationRepository applicationRepository,
            SpsErestService spsErestService,
            SpsetpolService spsetpolService,
            SpsetstuService spsetstuService,
            SpsetstyService spsetstyService) {
        this.service = service;
        this.serviceEstimateService = serviceEstimateService;
        this.spdppolmService = spdppolmService;
        this.spstrutmService = spstrutmService;
        this.spstaymtService = spstaymtService;
        this.spsErestRepository = spsErestRepository;
        this.applicationRepository = applicationRepository;
        this.spsErestService = spsErestService;
        this.spsetpolService = spsetpolService;
        this.spsetstuService = spsetstuService;
        this.spsetstyService = spsetstyService;
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

    // ========================
    // Filtered Application No lists for Add/Modify modes
    // ========================

    @GetMapping("/application-nos/unused")
    public ResponseEntity<List<ApplicationDropdownDto>> getUnusedApplicationNos(
            @RequestParam String deptId,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String status) {
        try {
            // Base list using existing filters if provided
            List<ApplicationDropdownDto> base = (applicationType != null && status != null)
                    ? service.getApplicationsByDeptTypeStatus(deptId, applicationType, status)
                    : applicationRepository.findApplicationNosByDeptId(deptId).stream()
                            .map(appNo -> new ApplicationDropdownDto(appNo, deptId))
                            .toList();

            List<String> usedNos = spsErestRepository.findUsedApplicationNosByDeptId(deptId);
            List<ApplicationDropdownDto> unused = base.stream()
                    .filter(dto -> dto != null && dto.getApplicationNo() != null && !usedNos.contains(dto.getApplicationNo()))
                    .toList();
            return ResponseEntity.ok(unused);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of());
        }
    }

    @GetMapping("/application-nos/used")
    public ResponseEntity<List<ApplicationDropdownDto>> getUsedApplicationNos(
            @RequestParam String deptId,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String status) {
        try {
            List<String> usedNos = spsErestRepository.findUsedApplicationNosByDeptId(deptId);
            // If filters provided, intersect with those
            List<ApplicationDropdownDto> filtered = (applicationType != null && status != null)
                    ? service.getApplicationsByDeptTypeStatus(deptId, applicationType, status).stream()
                            .filter(dto -> dto != null && usedNos.contains(dto.getApplicationNo()))
                            .toList()
                    : usedNos.stream().map(appNo -> new ApplicationDropdownDto(appNo, deptId)).toList();
            return ResponseEntity.ok(filtered);
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
            // Validate mode and enforce existence rules
            String mode = null;
            Object modeObj = frontendData.get("mode");
            if (modeObj != null) mode = modeObj.toString().trim().toUpperCase();

            Map<String, Object> connectionDetails = (Map<String, Object>) frontendData.get("connectionDetails");
            if (connectionDetails == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Missing connectionDetails",
                        "message", "connectionDetails is required"));
            }
            String applicationNo = String.valueOf(connectionDetails.getOrDefault("applicationNo", "")).trim();
            String deptId = String.valueOf(connectionDetails.getOrDefault("deptId", "")).trim();

            if (applicationNo.isEmpty() || deptId.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Missing identifiers",
                        "message", "applicationNo and deptId are required"));
            }

            boolean exists = spsErestRepository.existsByApplicationNoAndDeptId(applicationNo, deptId);

            if ("ADD".equals(mode)) {
                if (exists) {
                    return ResponseEntity.badRequest().body(Map.of(
                            "error", "Service estimate already exists for this application number",
                            "code", "ESTIMATE_EXISTS"));
                }
            } else if ("MODIFY".equals(mode)) {
                if (!exists) {
                    return ResponseEntity.badRequest().body(Map.of(
                            "error", "No existing service estimate for this application number",
                            "code", "ESTIMATE_NOT_FOUND"));
                }
            }

            return ResponseEntity.ok(serviceEstimateService.saveFromFrontendData(frontendData));
        } catch (Exception e) {
            e.printStackTrace();
            // Unwrap root cause for clearer client error messages
            Throwable root = e;
            while (root.getCause() != null && root.getCause() != root) {
                root = root.getCause();
            }

            String rawMsg = root.getMessage() != null ? root.getMessage() : e.getMessage();
            String message = rawMsg;

            // Friendly mappings for common cases
            if (rawMsg != null) {
                String m = rawMsg.toLowerCase();
                if (m.contains("invalid material codes") || m.contains("not in inmatm")) {
                    message = "Invalid material codes in poles/struts/stays. Please check MAT_CD values.";
                } else if (m.contains("application not found")) {
                    message = "Application not found; verify Application No and Department.";
                } else if (m.contains("constraint") || m.contains("unique") || m.contains("duplicate")) {
                    message = "Database constraint violation while saving. Check duplicates and required fields.";
                } else if (m.contains("timeout")) {
                    message = "Transaction timed out while saving. Please try again or reduce payload.";
                }
            }

            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to save service estimate data",
                    "message", message,
                    "rootMessage", rawMsg));
        }
    }

    // ========================
    // Service Estimate Deletion (Modify mode -> Delete)
    // ========================
    @DeleteMapping("/service-estimate/{applicationNo}/{deptId}")
    public ResponseEntity<?> deleteServiceEstimate(
            @PathVariable String applicationNo,
            @PathVariable String deptId,
            @RequestParam(name = "confirm", defaultValue = "false") boolean confirm) {
        try {
            if (!confirm) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Confirmation required",
                        "message", "Pass confirm=true to proceed with deletion."));
            }

            boolean exists = spsErestRepository.existsByApplicationNoAndDeptId(applicationNo, deptId);
            if (!exists) {
                return ResponseEntity.status(404).body(Map.of(
                        "error", "Service estimate not found",
                        "applicationNo", applicationNo,
                        "deptId", deptId));
            }

            serviceEstimateService.deleteServiceEstimate(applicationNo, deptId);

            // After deletion, this applicationNo should move to Add (unused) list automatically
            return ResponseEntity.ok(Map.of(
                    "status", "deleted",
                    "applicationNo", applicationNo,
                    "deptId", deptId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Failed to delete service estimate",
                    "message", e.getMessage()));
        }
    }

    // Alternate deletion endpoint using query params to avoid path issues with slashes
    @DeleteMapping("/service-estimate")
    public ResponseEntity<?> deleteServiceEstimateQuery(
            @RequestParam String applicationNo,
            @RequestParam String deptId,
            @RequestParam(name = "confirm", defaultValue = "false") boolean confirm) {
        try {
            if (!confirm) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Confirmation required",
                        "message", "Pass confirm=true to proceed with deletion."));
            }

            boolean exists = spsErestRepository.existsByApplicationNoAndDeptId(applicationNo, deptId);
            if (!exists) {
                return ResponseEntity.status(404).body(Map.of(
                        "error", "Service estimate not found",
                        "applicationNo", applicationNo,
                        "deptId", deptId));
            }

            serviceEstimateService.deleteServiceEstimate(applicationNo, deptId);
            return ResponseEntity.ok(Map.of(
                    "status", "deleted",
                    "applicationNo", applicationNo,
                    "deptId", deptId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Failed to delete service estimate",
                    "message", e.getMessage()));
        }
    }

    // ========================
    // Service Estimate fetch for Modify auto-fill
    // ========================
    @GetMapping("/service-estimate/sps-arest")
    public ResponseEntity<?> getSpsErest(
            @RequestParam String applicationNo,
            @RequestParam String deptId) {
        try {
            SpsErestDto dto = new SpsErestDto();
            var entity = spsErestService.findOne(applicationNo, deptId);
            // Map entity -> dto
            dto.setApplicationNo(applicationNo);
            dto.setDeptId(deptId);
            dto.setWiringType(entity.getWiringType());
            dto.setSecondCircuitLength(entity.getSecondCircuitLength());
            dto.setTotalLength(entity.getTotalLength());
            dto.setCableType(entity.getCableType());
            dto.setConversionLength(entity.getConversionLength());
            dto.setConversionLength2p(entity.getConversionLength2p());
            dto.setLoopCable(entity.getLoopCable());
            dto.setDistanceToSp(entity.getDistanceToSp());
            dto.setSin(entity.getSin());
            dto.setBusinessType(entity.getBusinessType());
            dto.setNoOfSpans(entity.getNoOfSpans());
            dto.setPoleno(entity.getPoleno());
            dto.setDistanceFromSs(entity.getDistanceFromSs());
            dto.setSubstation(entity.getSubstation());
            dto.setTransformerCapacity(entity.getTransformerCapacity());
            dto.setTransformerLoad(entity.getTransformerLoad());
            dto.setTransformerPeakLoad(entity.getTransformerPeakLoad());
            dto.setFeederControlType(entity.getFeederControlType());
            dto.setPhase(entity.getPhase());
            dto.setInsideLength(entity.getInsideLength());
            dto.setIsSyaNeeded(entity.getIsSyaNeeded());
            dto.setIsServiceConversion(entity.getIsServiceConversion());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body(Map.of(
                    "error", "Service estimate not found",
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

    // ========================
    // Sketch4 Read Endpoints (Saved Poles/Struts/Stays)
    // ========================
    @GetMapping("/service-estimate/sketch4/poles")
    public ResponseEntity<?> getSavedPoles(@RequestParam String applicationNo, @RequestParam String deptId) {
        try {
            var list = spsetpolService.findByApplicationAndDept(applicationNo, deptId);
            // Map entities to lightweight payload for frontend tables
            var payload = list.stream().map(e -> {
                var id = e.getId();
                return Map.of(
                        "selectPole", id.getMatCd(),
                        "poleType", id.getPoleType(),
                        "pointerType", id.getPointType(),
                        "connFrom", id.getFromConductor(),
                        "connTo", id.getToConductor(),
                        "qty", e.getMatQty()
                );
            }).toList();
            return ResponseEntity.ok(payload);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/service-estimate/sketch4/struts")
    public ResponseEntity<?> getSavedStruts(@RequestParam String applicationNo, @RequestParam String deptId) {
        try {
            var list = spsetstuService.findByApplicationAndDept(applicationNo, deptId);
            var payload = list.stream().map(e -> Map.of(
                    "type", e.getId().getMatCd(),
                    "qty", e.getMatQty()
            )).toList();
            return ResponseEntity.ok(payload);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/service-estimate/sketch4/stays")
    public ResponseEntity<?> getSavedStays(@RequestParam String applicationNo, @RequestParam String deptId) {
        try {
            var list = spsetstyService.findByApplicationAndDept(applicationNo, deptId);
            var payload = list.stream().map(e -> Map.of(
                    "type", e.getId().getMatCd(),
                    "stayType", e.getId().getStayType(),
                    "qty", e.getMatQty()
            )).toList();
            return ResponseEntity.ok(payload);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
