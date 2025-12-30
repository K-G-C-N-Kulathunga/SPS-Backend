package com.it.sps.dto;

import java.math.BigDecimal;

public class SpsetstuDto {

    // --- Required for PK ---
    private String applicationNo;   // maps to APPLICATION_NO
    private String deptId;          // maps to DEPT_ID
    private String matCd;           // maps to MAT_CD

    // --- Other fields ---
    private BigDecimal matQty;      // maps to MAT_QTY

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
}
