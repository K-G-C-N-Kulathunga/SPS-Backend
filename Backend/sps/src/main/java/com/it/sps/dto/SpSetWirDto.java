package com.it.sps.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SpSetWirDto {
    private String applicationNo;
    private String deptId;
    private List<WireDetail> wireDetails;

    public static class WireDetail {
        private String matCd;
        private String wireMode;
        private String wireType;
        private BigDecimal wireLength;

        public WireDetail() {}

        public WireDetail(String matCd, String wireMode, String wireType, BigDecimal wireLength) {
            this.matCd = matCd;
            this.wireMode = wireMode;
            this.wireType = wireType;
            this.wireLength = wireLength;
        }

        // Getters and Setters
        public String getMatCd() { return matCd; }
        public void setMatCd(String matCd) { this.matCd = matCd; }

        public String getWireMode() { return wireMode; }
        public void setWireMode(String wireMode) { this.wireMode = wireMode; }

        public String getWireType() { return wireType; }
        public void setWireType(String wireType) { this.wireType = wireType; }

        public BigDecimal getWireLength() { return wireLength; }
        public void setWireLength(BigDecimal wireLength) { this.wireLength = wireLength; }
    }

    // Getters and Setters
    public String getApplicationNo() { return applicationNo; }
    public void setApplicationNo(String applicationNo) { this.applicationNo = applicationNo; }

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public List<WireDetail> getWireDetails() { return wireDetails; }
    public void setWireDetails(List<WireDetail> wireDetails) { this.wireDetails = wireDetails; }

    // Helper method to create wire details from frontend form data
    public static SpSetWirDto fromFrontendData(String applicationNo, String deptId, Object sketch1Data) {
        SpSetWirDto dto = new SpSetWirDto();
        dto.setApplicationNo(applicationNo);
        dto.setDeptId(deptId);
        
        List<WireDetail> wireDetails = new ArrayList<>();
        
        // Map frontend fields to SPSETWIR table structure based on your requirements:
        // Second Circuit Conductor Type = WIRE_TYPE in SPSETWIR - When MAT_CD = "CIRCUIT"
        // Conductor Type = WIRE_TYPE - in SPSETWIR - When MAT_CD = "BARE"
        // Service Length (m) = WIRE_LENGTH -in SPSETWIR - When MAT_CD = "SERVICE"
        // Single Circuit Length (m) = WIRE_LENGTH - in SPSETWIR - When MAT_CD = "CIRCUIT"
        
        // Note: This method will be implemented in the service layer with proper Java reflection
        // or by creating a proper mapping structure
        
        dto.setWireDetails(wireDetails);
        return dto;
    }
}
