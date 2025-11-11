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

    public ServiceEstimateServiceImpl(SpsErestService spsErestService,
                                     SpSetWirService spSetWirService,
                                     WireDataMapper wireDataMapper,
                                     ApplicationRepository applicationRepository) {
        this.spsErestService = spsErestService;
        this.spSetWirService = spSetWirService;
        this.wireDataMapper = wireDataMapper;
        this.applicationRepository = applicationRepository;
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
            
            System.out.println("DEBUG: About to create SpSetWirDto for applicationNo=" + applicationNo + ", deptId=" + deptId);
            
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

            // Save both using the existing method
            System.out.println("DEBUG: About to call saveServiceEstimate with spsErestDto: " + spsErestDto);
            System.out.println("DEBUG: About to call saveServiceEstimate with spSetWirDto: " + spSetWirDto);
            
            return saveServiceEstimate(new ServiceEstimateDto(spsErestDto, spSetWirDto));
            
        } catch (Exception e) {
            System.out.println("DEBUG: ServiceEstimateServiceImpl - Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save frontend data: " + e.getMessage(), e);
        }
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
        
        // Map sketch2 data
        Map<String, Object> sketch2 = (Map<String, Object>) frontendData.get("sketch2");
        System.out.println("DEBUG: Mapping sketch2: " + sketch2);
        dto.setDistanceToSp(parseBigDecimal(sketch2.get("distanceToServicePoint")));
        dto.setSin(getStringValue(sketch2, "sinNumber"));
        dto.setNoOfSpans(parseBigDecimal(sketch2.get("numberOfRanges")));
        dto.setPoleno(getStringValue(sketch2, "poleNumber"));
        dto.setIsSyaNeeded(normalizeYesNoValue(getStringValue(sketch2, "isSyaNeeded"))); // Map from frontend data
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
        
        // Set default values for required fields that don't have mapping
        dto.setIsStandardVc("Y"); // Default value
        dto.setInsideLength(BigDecimal.ZERO); // Default value
        
        System.out.println("DEBUG: Final SpsErestDto: " + dto);
        return dto;
    }

    private String getStringValue(Map<String, Object> data, String key) {
        Object value = data.get(key);
        return value != null ? value.toString().trim() : null;
    }

    private String normalizeYesNoValue(String value) {
        if (value == null) return null;
        String normalized = value.trim().toLowerCase();
        if (normalized.equals("yes") || normalized.equals("y")) {
            return "Y";
        } else if (normalized.equals("no") || normalized.equals("n")) {
            return "N";
        }
        return value.trim();
    }

    private BigDecimal parseBigDecimal(Object value) {
        System.out.println("DEBUG: parseBigDecimal - Input value: " + value + " (type: " + (value != null ? value.getClass().getSimpleName() : "null") + ")");
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
            System.out.println("DEBUG: parseBigDecimal - NumberFormatException for value: " + value + " - " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
