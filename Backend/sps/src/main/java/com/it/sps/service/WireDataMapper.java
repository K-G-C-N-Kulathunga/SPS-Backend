package com.it.sps.service;

import com.it.sps.dto.SpSetWirDto;
import com.it.sps.dto.SpSetWirDto.WireDetail;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WireDataMapper {

    /**
     * Maps frontend sketch1 data to SPSETWIR table structure
     * Based on the requirements:
     * - Second Circuit Conductor Type = WIRE_TYPE in SPSETWIR - When MAT_CD = "CIRCUIT"
     * - Conductor Type = WIRE_TYPE - in SPSETWIR - When MAT_CD = "BARE"
     * - Service Length (m) = WIRE_LENGTH -in SPSETWIR - When MAT_CD = "SERVICE"
     * - Single Circuit Length (m) = WIRE_LENGTH - in SPSETWIR - When MAT_CD = "CIRCUIT"
     */
    public SpSetWirDto mapToSpSetWirDto(String applicationNo, String deptId, Map<String, Object> sketch1Data) {
        System.out.println("DEBUG: WireDataMapper - applicationNo=" + applicationNo + ", deptId=" + deptId);
        System.out.println("DEBUG: WireDataMapper - sketch1Data=" + sketch1Data);
        
        SpSetWirDto dto = new SpSetWirDto();
        dto.setApplicationNo(applicationNo);
        dto.setDeptId(deptId);
        
        List<WireDetail> wireDetails = new ArrayList<>();
        
        // Map SERVICE wire detail
        if (sketch1Data.containsKey("serviceLength") && sketch1Data.get("serviceLength") != null && 
            !sketch1Data.get("serviceLength").toString().trim().isEmpty()) {
            String conductorType = getStringValue(sketch1Data, "conductorType", "ABC-95+70");
            BigDecimal serviceLength = parseBigDecimal(sketch1Data.get("serviceLength"));
            
            wireDetails.add(new WireDetail(
                "SERVICE", 
                "OH", // Overhead
                conductorType,
                serviceLength
            ));
        }
        
        // Map CIRCUIT wire detail (Single Circuit)
        if (sketch1Data.containsKey("singleCircuitLength") && sketch1Data.get("singleCircuitLength") != null && 
            !sketch1Data.get("singleCircuitLength").toString().trim().isEmpty()) {
            String conductorType = getStringValue(sketch1Data, "conductorType", "ABC-95+70");
            BigDecimal circuitLength = parseBigDecimal(sketch1Data.get("singleCircuitLength"));
            
            wireDetails.add(new WireDetail(
                "CIRCUIT",
                "OH", // Overhead
                conductorType,
                circuitLength
            ));
        }
        
        // Map SECOND CIRCUIT wire detail
        if (sketch1Data.containsKey("secondCircuitLength") && sketch1Data.get("secondCircuitLength") != null && 
            !sketch1Data.get("secondCircuitLength").toString().trim().isEmpty()) {
            String conductorType = getStringValue(sketch1Data, "secondCircuitConductorType", "ABC-95+70");
            BigDecimal secondCircuitLength = parseBigDecimal(sketch1Data.get("secondCircuitLength"));
            
            wireDetails.add(new WireDetail(
                "CIRCUIT",
                "OH", // Overhead
                conductorType,
                secondCircuitLength
            ));
        }
        
        // Map BARE wire detail (if conductor type is different from default)
        String defaultConductorType = "ABC-95+70";
        String conductorType = getStringValue(sketch1Data, "conductorType", defaultConductorType);
        if (!defaultConductorType.equals(conductorType)) {
            wireDetails.add(new WireDetail(
                "BARE",
                "OH", // Overhead
                conductorType,
                BigDecimal.ZERO // Length not specified for bare wire
            ));
        }
        
        dto.setWireDetails(wireDetails);
        
        // Ensure at least one wire detail exists
        if (wireDetails.isEmpty()) {
            System.out.println("DEBUG: WireDataMapper - No wire details created, adding default");
            wireDetails.add(new WireDetail(
                "SERVICE",
                "OH",
                "ABC-95+70",
                BigDecimal.ZERO
            ));
            dto.setWireDetails(wireDetails);
        }
        
        System.out.println("DEBUG: WireDataMapper - Created wire details: " + wireDetails);
        System.out.println("DEBUG: WireDataMapper - Final DTO: " + dto);
        return dto;
    }
    
    private String getStringValue(Map<String, Object> data, String key, String defaultValue) {
        Object value = data.get(key);
        if (value == null || value.toString().trim().isEmpty()) {
            return defaultValue;
        }
        return value.toString().trim();
    }
    
    private BigDecimal parseBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        try {
            if (value instanceof Number) {
                return new BigDecimal(value.toString());
            } else if (value instanceof String) {
                String strValue = value.toString().trim();
                if (strValue.isEmpty()) {
                    return BigDecimal.ZERO;
                }
                return new BigDecimal(strValue);
            }
        } catch (NumberFormatException e) {
            // Log error if needed
        }
        return BigDecimal.ZERO;
    }
}
