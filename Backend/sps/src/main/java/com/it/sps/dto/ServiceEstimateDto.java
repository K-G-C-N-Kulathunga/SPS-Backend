package com.it.sps.dto;

import java.math.BigDecimal;
import java.util.List;

public class ServiceEstimateDto {
    private SpsErestDto spsErestData;
    private SpSetWirDto spSetWirData;

    public ServiceEstimateDto() {}

    public ServiceEstimateDto(SpsErestDto spsErestData, SpSetWirDto spSetWirData) {
        this.spsErestData = spsErestData;
        this.spSetWirData = spSetWirData;
    }

    // Getters and Setters
    public SpsErestDto getSpsErestData() { return spsErestData; }
    public void setSpsErestData(SpsErestDto spsErestData) { this.spsErestData = spsErestData; }

    public SpSetWirDto getSpSetWirData() { return spSetWirData; }
    public void setSpSetWirData(SpSetWirDto spSetWirData) { this.spSetWirData = spSetWirData; }
}

