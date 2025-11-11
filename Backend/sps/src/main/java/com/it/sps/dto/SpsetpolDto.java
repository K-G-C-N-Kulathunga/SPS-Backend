package com.it.sps.dto;

import java.math.BigDecimal;

public class SpsetpolDto {

    // --- Required for PK ---
    private String applicationNo;   // maps to APPLICATION_NO
    private String deptId;          // maps to DEPT_ID
    private String pointType;       // maps to POINT_TYPE
    private String poleType;        // maps to POLE_TYPE
    private String fromConductor;   // maps to FROM_CONDUCTOR
    private String toConductor;     // maps to TO_CONDUCTOR
    private String matCd;           // maps to MAT_CD

    // --- Other fields ---
    private BigDecimal matQty;          // maps to MAT_QTY
    private String jobCategoryId;       // maps to JOB_CATEGORY_ID
    private String description;         // maps to DESCRIPTION

    // --- Getters & Setters ---
    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getPoleType() {
        return poleType;
    }

    public void setPoleType(String poleType) {
        this.poleType = poleType;
    }

    public String getFromConductor() {
        return fromConductor;
    }

    public void setFromConductor(String fromConductor) {
        this.fromConductor = fromConductor;
    }

    public String getToConductor() {
        return toConductor;
    }

    public void setToConductor(String toConductor) {
        this.toConductor = toConductor;
    }

    public String getMatCd() {
        return matCd;
    }

    public void setMatCd(String matCd) {
        this.matCd = matCd;
    }

    public BigDecimal getMatQty() {
        return matQty;
    }

    public void setMatQty(BigDecimal matQty) {
        this.matQty = matQty;
    }

    public String getJobCategoryId() {
        return jobCategoryId;
    }

    public void setJobCategoryId(String jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
