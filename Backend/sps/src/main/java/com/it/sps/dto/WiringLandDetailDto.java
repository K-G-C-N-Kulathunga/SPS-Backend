package com.it.sps.dto;


import java.math.BigDecimal;

public class WiringLandDetailDto {

    private String applicationId;
    private String deptId;

    //Service Location Details
    private String serviceStreetAddress;
    private String serviceCity;
    private String serviceSuburb;
    private String servicePostalCode;
    private String assessmentNo;
    private String neighboursAccNo;
    private String ownership;

    //Details of Wiring
    private BigDecimal metalCrusher;
    private BigDecimal sawMills;
    private BigDecimal weldingPlant;
    private BigDecimal phase;
    private String customerCategory;
    private String tariffCatCode;
    private String tariffCode;
    private BigDecimal connectionType;
    private String customerType;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getServiceStreetAddress() {
        return serviceStreetAddress;
    }

    public void setServiceStreetAddress(String serviceStreetAddress) {
        this.serviceStreetAddress = serviceStreetAddress;
    }

    public String getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(String serviceCity) {
        this.serviceCity = serviceCity;
    }

    public String getServiceSuburb() {
        return serviceSuburb;
    }

    public void setServiceSuburb(String serviceSuburb) {
        this.serviceSuburb = serviceSuburb;
    }

    public String getServicePostalCode() {
        return servicePostalCode;
    }

    public void setServicePostalCode(String servicePostalCode) {
        this.servicePostalCode = servicePostalCode;
    }

    public String getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getNeighboursAccNo() {
        return neighboursAccNo;
    }

    public void setNeighboursAccNo(String neighboursAccNo) {
        this.neighboursAccNo = neighboursAccNo;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public BigDecimal getMetalCrusher() {
        return metalCrusher;
    }

    public void setMetalCrusher(BigDecimal metalCrusher) {
        this.metalCrusher = metalCrusher;
    }

    public BigDecimal getSawMills() {
        return sawMills;
    }

    public void setSawMills(BigDecimal sawMills) {
        this.sawMills = sawMills;
    }

    public BigDecimal getWeldingPlant() {
        return weldingPlant;
    }

    public void setWeldingPlant(BigDecimal weldingPlant) {
        this.weldingPlant = weldingPlant;
    }

    public BigDecimal getPhase() {
        return phase;
    }

    public void setPhase(BigDecimal phase) {
        this.phase = phase;
    }

    public String getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(String customerCategory) {
        this.customerCategory = customerCategory;
    }

    public String getTariffCatCode() {
        return tariffCatCode;
    }

    public void setTariffCatCode(String tariffCatCode) {
        this.tariffCatCode = tariffCatCode;
    }

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public BigDecimal getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(BigDecimal connectionType) {
        this.connectionType = connectionType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}

