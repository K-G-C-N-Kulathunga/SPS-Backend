package com.it.sps.dto;

import java.math.BigDecimal;

public class SpsErestDto {

    // --- Required for PK ---
    private String applicationNo;   // maps to APPLICATION_NO
    private String deptId;          // maps to DEPT_ID (NOT NULL)

    // --- Required business fields per your list ---
    private String wiringType;                 // WIRING_TYPE
    private BigDecimal secondCircuitLength;    // SECOND_CIRCUIT_LENGTH
    private String totalLength;                // TOTAL_LENGTH
    private String cableType;                  // CABLE_TYPE
    private BigDecimal conversionLength;       // CONVERSION_LENGTH
    private BigDecimal conversionLength2p;     // CONVERSION_LENGTH_2P
    private String loopCable;                  // LOOP_CABLE
    private BigDecimal distanceToSp;           // DISTANCE_TO_SP
    private String sin;                        // SIN
    private String isSyaNeeded;                // IS_SYA_NEEDED
    private String businessType;               // BUSINESS_TYPE
    private BigDecimal noOfSpans;              // NO_OF_SPANS
    private String isStandardVc;               // IS_STANDARD_VC
    private String poleno;                     // POLENO
    private BigDecimal distanceFromSs;         // DISTANCE_FROM_SS
    private String substation;                 // SUBSTATION
    private String transformerCapacity;        // TRANSFORMER_CAPACITY
    private String transformerLoad;            // TRANSFORMER_LOAD
    private String transformerPeakLoad;        // TRANSFORMER_PEAK_LOAD
    private String feederControlType;          // FEEDER_CONTROL_TYPE
    private String phase;                      // PHASE
    private BigDecimal insideLength;
    private String isServiceConversion;        // IS_SERVICE_CONVERSION
    // --- Getters & Setters ---
    public String getApplicationNo() { return applicationNo; }
    public void setApplicationNo(String applicationNo) { this.applicationNo = applicationNo; }

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public String getWiringType() { return wiringType; }
    public void setWiringType(String wiringType) { this.wiringType = wiringType; }

    public BigDecimal getSecondCircuitLength() { return secondCircuitLength; }
    public void setSecondCircuitLength(BigDecimal secondCircuitLength) { this.secondCircuitLength = secondCircuitLength; }

    public String getTotalLength() { return totalLength; }
    public void setTotalLength(String totalLength) { this.totalLength = totalLength; }

    public String getCableType() { return cableType; }
    public void setCableType(String cableType) { this.cableType = cableType; }

    public BigDecimal getConversionLength() { return conversionLength; }
    public void setConversionLength(BigDecimal conversionLength) { this.conversionLength = conversionLength; }

    public BigDecimal getConversionLength2p() { return conversionLength2p; }
    public void setConversionLength2p(BigDecimal conversionLength2p) { this.conversionLength2p = conversionLength2p; }

    public String getLoopCable() { return loopCable; }
    public void setLoopCable(String loopCable) { this.loopCable = loopCable; }

    public BigDecimal getDistanceToSp() { return distanceToSp; }
    public void setDistanceToSp(BigDecimal distanceToSp) { this.distanceToSp = distanceToSp; }

    public String getSin() { return sin; }
    public void setSin(String sin) { this.sin = sin; }

    public String getIsSyaNeeded() { return isSyaNeeded; }
    public void setIsSyaNeeded(String isSyaNeeded) { this.isSyaNeeded = isSyaNeeded; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public BigDecimal getNoOfSpans() { return noOfSpans; }
    public void setNoOfSpans(BigDecimal noOfSpans) { this.noOfSpans = noOfSpans; }

    public String getIsStandardVc() { return isStandardVc; }
    public void setIsStandardVc(String isStandardVc) { this.isStandardVc = isStandardVc; }

    public String getPoleno() { return poleno; }
    public void setPoleno(String poleno) { this.poleno = poleno; }

    public BigDecimal getDistanceFromSs() { return distanceFromSs; }
    public void setDistanceFromSs(BigDecimal distanceFromSs) { this.distanceFromSs = distanceFromSs; }

    public String getSubstation() { return substation; }
    public void setSubstation(String substation) { this.substation = substation; }

    public String getTransformerCapacity() { return transformerCapacity; }
    public void setTransformerCapacity(String transformerCapacity) { this.transformerCapacity = transformerCapacity; }

    public String getTransformerLoad() { return transformerLoad; }
    public void setTransformerLoad(String transformerLoad) { this.transformerLoad = transformerLoad; }

    public String getTransformerPeakLoad() { return transformerPeakLoad; }
    public void setTransformerPeakLoad(String transformerPeakLoad) { this.transformerPeakLoad = transformerPeakLoad; }

    public String getFeederControlType() { return feederControlType; }
    public void setFeederControlType(String feederControlType) { this.feederControlType = feederControlType; }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }

    public BigDecimal getInsideLength() {
        return insideLength;
    }

    public void setInsideLength(BigDecimal insideLength) {
        this.insideLength = insideLength;
    }

    public String getIsServiceConversion() {
        return isServiceConversion;
    }

    public void setIsServiceConversion(String isServiceConversion) {
        this.isServiceConversion = isServiceConversion;
    }

}

