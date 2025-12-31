package com.it.sps.service;

import com.it.sps.dto.ServiceEstimateDto;
import com.it.sps.dto.SpsErestDto;
import com.it.sps.dto.SpSetWirDto;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpSetWir;
import com.it.sps.entity.Application;
import com.it.sps.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServiceEstimateServiceImpl implements ServiceEstimateService {

    private final SpsErestService spsErestService;
    private final SpSetWirService spSetWirService;
    private final WireDataMapper wireDataMapper;
    private final ApplicationRepository applicationRepository;
    private final SpsetpolService spsetpolService;
    private final SpsetstuService spsetstuService;
    private final SpsetstyService spsetstyService;

    public ServiceEstimateServiceImpl(SpsErestService spsErestService,
            SpSetWirService spSetWirService,
            WireDataMapper wireDataMapper,
            ApplicationRepository applicationRepository,
            SpsetpolService spsetpolService,
            SpsetstuService spsetstuService,
            SpsetstyService spsetstyService) {
        this.spsErestService = spsErestService;
        this.spSetWirService = spSetWirService;
        this.wireDataMapper = wireDataMapper;
        this.applicationRepository = applicationRepository;
        this.spsetpolService = spsetpolService;
        this.spsetstuService = spsetstuService;
        this.spsetstyService = spsetstyService;
    }

    @Override
    public ServiceEstimateResult saveServiceEstimate(ServiceEstimateDto dto) {
        // Validate input
        if (dto == null) {
            throw new IllegalArgumentException("ServiceEstimateDto cannot be null");
        }
        if (dto.getSpsErestData() == null) {
            throw new IllegalArgumentException("SpsErest data cannot be null");
        }
        if (dto.getSpSetWirData() == null) {
            throw new IllegalArgumentException("SpSetWir data cannot be null");
        }

        // Ensure both DTOs have the same application and department
        String applicationNo = dto.getSpsErestData().getApplicationNo();
        String deptId = dto.getSpsErestData().getDeptId();

        if (!applicationNo.equals(dto.getSpSetWirData().getApplicationNo()) ||
                !deptId.equals(dto.getSpSetWirData().getDeptId())) {
            throw new IllegalArgumentException("Application number and department ID must match in both DTOs");
        }

        try {
            // Save to SPSEREST table first
            SpsErest savedSpsErest = spsErestService.save(dto.getSpsErestData());

            // Save to SPSETWIR table
            List<SpSetWir> savedSpSetWir = spSetWirService.save(dto.getSpSetWirData());

            // If both operations succeed, return the result
            return new ServiceEstimateResult(savedSpsErest, savedSpSetWir);

        } catch (Exception e) {
            // If any operation fails, the transaction will be rolled back automatically
            // due to @Transactional annotation
            throw new RuntimeException("Failed to save service estimate data: " + e.getMessage(), e);
        }
    }

    @Override
    public ServiceEstimateResult saveFromFrontendData(Map<String, Object> frontendData) {
        // Validate input
        if (frontendData == null) {
            throw new IllegalArgumentException("FrontendFormDataDto cannot be null");
        }
        if (!frontendData.containsKey("connectionDetails") || frontendData.get("connectionDetails") == null) {
            throw new IllegalArgumentException("Connection details cannot be null");
        }
        if (!frontendData.containsKey("sketch1") || frontendData.get("sketch1") == null) {
            throw new IllegalArgumentException("Sketch1 data cannot be null");
        }

        // Debug logging
        System.out.println("DEBUG: Received frontend data: " + frontendData);
        System.out.println("DEBUG: Connection details: " + frontendData.get("connectionDetails"));
        System.out.println("DEBUG: Sketch1: " + frontendData.get("sketch1"));
        System.out.println("DEBUG: Sketch2: " + frontendData.get("sketch2"));
        System.out.println("DEBUG: Sketch3: " + frontendData.get("sketch3"));

        try {
            // Extract application number and department ID from connection details
            Map<String, Object> connectionDetails = (Map<String, Object>) frontendData.get("connectionDetails");
            String applicationNo = getStringValue(connectionDetails, "applicationNo");
            String deptId = getStringValue(connectionDetails, "deptId");

            if (applicationNo == null || applicationNo.trim().isEmpty()) {
                throw new IllegalArgumentException("Application number is required");
            }
            if (deptId == null || deptId.trim().isEmpty()) {
                throw new IllegalArgumentException("Department ID is required");
            }

            // Debug: Check if application exists
            try {
                Application app = applicationRepository.findAllWithApplicant(applicationNo);
                System.out.println("DEBUG: Application lookup result: " + app);
                if (app == null) {
                    System.out.println("DEBUG: WARNING - Application not found in database!");

                    // Debug: Show what applications exist
                    List<Application> allApps = applicationRepository.findAllApplications();
                    System.out.println("DEBUG: All applications in database: " + allApps.size());
                    for (Application existingApp : allApps) {
                        System.out.println("DEBUG: - " + existingApp.getApplicationNo());
                    }
                }
            } catch (Exception e) {
                System.out.println("DEBUG: Application lookup exception: " + e.getMessage());
                e.printStackTrace();
            }

            // Create SpsErestDto from frontend data
            SpsErestDto spsErestDto = createSpsErestDto(frontendData, applicationNo, deptId);

            System.out.println(
                    "DEBUG: About to create SpSetWirDto for applicationNo=" + applicationNo + ", deptId=" + deptId);

            // Create SpSetWirDto from frontend data using the mapper
            Map<String, Object> sketch1 = (Map<String, Object>) frontendData.get("sketch1");
            Map<String, Object> sketch2 = (Map<String, Object>) frontendData.get("sketch2");
            Map<String, Object> sketch3 = (Map<String, Object>) frontendData.get("sketch3");

            // Debug: Validate data structure
            System.out.println("DEBUG: Validating data structure...");
            if (sketch1 == null || sketch1.isEmpty()) {
                System.out.println("DEBUG: ERROR - Sketch1 data is empty!");
            } else {
                System.out.println("DEBUG: Sketch1 fields: " + sketch1.keySet());
            }

            if (sketch2 == null || sketch2.isEmpty()) {
                System.out.println("DEBUG: ERROR - Sketch2 data is empty!");
            } else {
                System.out.println("DEBUG: Sketch2 fields: " + sketch2.keySet());
            }

            if (sketch3 == null || sketch3.isEmpty()) {
                System.out.println("DEBUG: ERROR - Sketch3 data is empty!");
            } else {
                System.out.println("DEBUG: Sketch3 fields: " + sketch3.keySet());
            }

            SpSetWirDto spSetWirDto = wireDataMapper.mapToSpSetWirDto(applicationNo, deptId, sketch1);

            // Save SPSEREST and SPSETWIR first (existing behavior)
            ServiceEstimateResult baseResult = saveServiceEstimate(new ServiceEstimateDto(spsErestDto, spSetWirDto));

            // Now handle sketch4 if present
            Map<String, Object> sketch4 = (Map<String, Object>) frontendData.get("sketch4");
            if (sketch4 != null) {
                System.out.println("DEBUG: Sketch4 present, parsing poles/struts/stays: " + sketch4.keySet());
                Sketch4Result saved = saveSketch4(applicationNo, deptId, sketch4);
                // attach to response
                if (saved != null) {
                    baseResult.setSpsetpolList(saved.spsetpolList);
                    baseResult.setSpsetstuList(saved.spsetstuList);
                    baseResult.setSpsetstyList(saved.spsetstyList);
                }
            } else {
                System.out.println("DEBUG: Sketch4 not provided; skipping SPSETPOL/STU/STY save");
            }

            return baseResult;

        } catch (IllegalArgumentException e) {
            // bubble up as-is so controller returns 400 with the message
            throw e;
        } catch (Exception e) {
            System.out.println("DEBUG: ServiceEstimateServiceImpl - Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save frontend data: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteServiceEstimate(String applicationNo, String deptId) {
        if (applicationNo == null || applicationNo.isBlank()) {
            throw new IllegalArgumentException("Application number is required");
        }
        if (deptId == null || deptId.isBlank()) {
            throw new IllegalArgumentException("Department ID is required");
        }

        // Delete child tables first, then parent (SPSEREST)
        // All operations are within the service's transactional boundary
        try {
            // Remove sketch4 related rows
            spsetpolService.deleteByApplicationAndDept(applicationNo, deptId);
            spsetstuService.deleteByApplicationAndDept(applicationNo, deptId);
            spsetstyService.deleteByApplicationAndDept(applicationNo, deptId);

            // Remove wire details
            spSetWirService.deleteByApplicationAndDept(applicationNo, deptId);

            // Finally remove main service estimate
            spsErestService.delete(applicationNo, deptId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete service estimate: " + e.getMessage(), e);
        }
    }

    private Sketch4Result saveSketch4(String applicationNo, String deptId, Map<String, Object> sketch4) {
        Sketch4Result result = new Sketch4Result();
        // Parse poles -> SPSETPOL
        Object polesObj = sketch4.get("poles");
        if (polesObj instanceof List) {
            List<?> poles = (List<?>) polesObj;
            List<com.it.sps.dto.SpsetpolDto> polDtos = new java.util.ArrayList<>();
            for (Object o : poles) {
                if (!(o instanceof Map))
                    continue;
                Map<String, Object> row = (Map<String, Object>) o;
                com.it.sps.dto.SpsetpolDto d = new com.it.sps.dto.SpsetpolDto();
                d.setApplicationNo(applicationNo);
                d.setDeptId(deptId);
                // Map fields from UI naming to DB columns
                d.setPointType(getStringValue(row, "pointerType"));
                d.setPoleType(getStringValue(row, "poleType"));
                d.setFromConductor(getStringValue(row, "connFrom"));
                d.setToConductor(getStringValue(row, "connTo"));
                // Accept both 'Selectpole' and 'selectPole'
                String matCd = getStringValue(row, "Selectpole");
                if (matCd == null || matCd.isEmpty()) {
                    matCd = getStringValue(row, "selectPole");
                }
                d.setMatCd(matCd);
                d.setMatQty(parseBigDecimal(row.get("qty")));
                // Optional fields not present in UI for now
                d.setJobCategoryId(null);
                d.setDescription(null);
                // Only add if mandatory keys are present
                if (d.getMatCd() != null && d.getPointType() != null && d.getPoleType() != null
                        && d.getFromConductor() != null && d.getToConductor() != null) {
                    polDtos.add(d);
                }
            }
            if (!polDtos.isEmpty()) {
                System.out.println("DEBUG: Saving SPSETPOL rows: " + polDtos.size());
                result.spsetpolList = spsetpolService.saveAll(applicationNo, deptId, polDtos);
            }
        }

        // Parse struts -> SPSETSTU
        Object strutsObj = sketch4.get("struts");
        if (strutsObj instanceof List) {
            List<?> struts = (List<?>) strutsObj;
            List<com.it.sps.dto.SpsetstuDto> stuDtos = new java.util.ArrayList<>();
            for (Object o : struts) {
                if (!(o instanceof Map))
                    continue;
                Map<String, Object> row = (Map<String, Object>) o;
                com.it.sps.dto.SpsetstuDto d = new com.it.sps.dto.SpsetstuDto();
                d.setApplicationNo(applicationNo);
                d.setDeptId(deptId);
                d.setMatCd(getStringValue(row, "type"));
                d.setMatQty(parseBigDecimal(row.get("qty")));
                if (d.getMatCd() != null) {
                    stuDtos.add(d);
                }
            }
            if (!stuDtos.isEmpty()) {
                System.out.println("DEBUG: Saving SPSETSTU rows: " + stuDtos.size());
                result.spsetstuList = spsetstuService.saveAll(applicationNo, deptId, stuDtos);
            }
        }

        // Parse stays -> SPSETSTY
        Object staysObj = sketch4.get("stays");
        if (staysObj instanceof List) {
            List<?> stays = (List<?>) staysObj;
            List<com.it.sps.dto.SpsetstyDto> styDtos = new java.util.ArrayList<>();
            for (Object o : stays) {
                if (!(o instanceof Map))
                    continue;
                Map<String, Object> row = (Map<String, Object>) o;
                com.it.sps.dto.SpsetstyDto d = new com.it.sps.dto.SpsetstyDto();
                d.setApplicationNo(applicationNo);
                d.setDeptId(deptId);
                d.setMatCd(getStringValue(row, "type"));
                // UI has both type and stayType; map stayType to PK stayType
                String stayType = getStringValue(row, "stayType");
                d.setStayType(stayType != null ? stayType : "NORMAL");
                d.setMatQty(parseBigDecimal(row.get("qty")));
                if (d.getMatCd() != null) {
                    styDtos.add(d);
                }
            }
            if (!styDtos.isEmpty()) {
                System.out.println("DEBUG: Saving SPSETSTY rows: " + styDtos.size());
                result.spsetstyList = spsetstyService.saveAll(applicationNo, deptId, styDtos);
            }
        }
        return result;
    }

    private static class Sketch4Result {
        java.util.List<com.it.sps.entity.Spsetpol> spsetpolList;
        java.util.List<com.it.sps.entity.Spsetstu> spsetstuList;
        java.util.List<com.it.sps.entity.Spsetsty> spsetstyList;
    }

    private SpsErestDto createSpsErestDto(Map<String, Object> frontendData, String applicationNo, String deptId) {
        System.out.println("DEBUG: Creating SpsErestDto for applicationNo=" + applicationNo + ", deptId=" + deptId);

        SpsErestDto dto = new SpsErestDto();
        dto.setApplicationNo(applicationNo);
        dto.setDeptId(deptId);

        // Map connection details
        Map<String, Object> connectionDetails = (Map<String, Object>) frontendData.get("connectionDetails");
        System.out.println("DEBUG: Mapping connection details: " + connectionDetails);
        dto.setPhase(getStringValue(connectionDetails, "phase"));
        // Don't set business type here, it will be set from sketch2

        // Map sketch1 data
        Map<String, Object> sketch1 = (Map<String, Object>) frontendData.get("sketch1");
        System.out.println("DEBUG: Mapping sketch1: " + sketch1);
        dto.setWiringType(getStringValue(sketch1, "wireType"));
        dto.setSecondCircuitLength(parseBigDecimal(sketch1.get("secondCircuitLength")));
        dto.setTotalLength(getStringValue(sketch1, "totalLineLength"));
        dto.setCableType(getStringValue(sketch1, "cableType"));
        dto.setConversionLength(parseBigDecimal(sketch1.get("conversion1P3P")));
        dto.setConversionLength2p(parseBigDecimal(sketch1.get("conversion2P3P")));
        dto.setLoopCable(normalizeYesNoValue(getStringValue(sketch1, "loopService")));
        // Map New Length Within Premises -> insideLength (accept multiple key variants for robustness)
        BigDecimal insideLen = parseBigDecimal(sketch1.get("newLengthWithinPremises"));
        if (insideLen == null || insideLen.compareTo(BigDecimal.ZERO) == 0) {
            insideLen = parseBigDecimal(sketch1.get("insideLength"));
        }
        dto.setInsideLength(insideLen);

        // Map sketch2 data
        Map<String, Object> sketch2 = (Map<String, Object>) frontendData.get("sketch2");
        System.out.println("DEBUG: Mapping sketch2: " + sketch2);
        dto.setDistanceToSp(parseBigDecimal(sketch2.get("distanceToServicePoint")));
        dto.setSin(getStringValue(sketch2, "sinNumber"));
        dto.setNoOfSpans(parseBigDecimal(sketch2.get("numberOfRanges")));
        dto.setPoleno(getStringValue(sketch2, "poleNumber"));
        dto.setIsSyaNeeded(normalizeYesNoValue(getStringValue(sketch2, "isSyaNeeded"))); // Map from frontend data
        dto.setIsServiceConversion(getStringValue(sketch2, "isServiceConversion"));
        dto.setBusinessType(getStringValue(sketch2, "businessType")); // Business type from sketch2

        // Map sketch3 data
        Map<String, Object> sketch3 = (Map<String, Object>) frontendData.get("sketch3");
        System.out.println("DEBUG: Mapping sketch3: " + sketch3);
        dto.setDistanceFromSs(parseBigDecimal(sketch3.get("distanceFromSS")));
        dto.setSubstation(getStringValue(sketch3, "substation"));
        dto.setTransformerCapacity(getStringValue(sketch3, "transformerCapacity"));
        dto.setTransformerLoad(getStringValue(sketch3, "transformerLoad"));
        dto.setTransformerPeakLoad(getStringValue(sketch3, "transformerPeakLoad"));
        dto.setFeederControlType(getStringValue(sketch3, "feederControlType"));
        dto.setPhase(getStringValue(sketch3, "phase"));

        // Set default values for required fields that don't have explicit mapping
        dto.setIsStandardVc("Y"); // Default value

        System.out.println("DEBUG: Final SpsErestDto: " + dto);
        return dto;
    }

    private String getStringValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        return value != null ? value.toString().trim() : null;
    }

    private String normalizeYesNoValue(String value) {
        if (value == null)
            return null;
        String normalized = value.trim().toLowerCase();
        if (normalized.equals("yes") || normalized.equals("y")) {
            return "Y";
        } else if (normalized.equals("no") || normalized.equals("n")) {
            return "N";
        }
        return value.trim();
    }

    private BigDecimal parseBigDecimal(Object value) {
        System.out.println("DEBUG: parseBigDecimal - Input value: " + value + " (type: "
                + (value != null ? value.getClass().getSimpleName() : "null") + ")");
        if (value == null) {
            return BigDecimal.ZERO;
        }
        try {
            if (value instanceof Number) {
                BigDecimal result = new BigDecimal(value.toString());
                System.out.println("DEBUG: parseBigDecimal - Parsed Number: " + result);
                return result;
            } else if (value instanceof String) {
                String strValue = value.toString().trim();
                if (strValue.isEmpty()) {
                    return BigDecimal.ZERO;
                }
                BigDecimal result = new BigDecimal(strValue);
                System.out.println("DEBUG: parseBigDecimal - Parsed String: " + result);
                return result;
            }
        } catch (NumberFormatException e) {
            System.out.println(
                    "DEBUG: parseBigDecimal - NumberFormatException for value: " + value + " - " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
