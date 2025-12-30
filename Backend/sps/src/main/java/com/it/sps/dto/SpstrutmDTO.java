package com.it.sps.dto;

public class SpstrutmDTO {

    private String deptId;
    private String matCd;
    private Double matQty;

    public SpstrutmDTO() {}

    public SpstrutmDTO(String deptId, String matCd, Double matQty) {
        this.deptId = deptId;
        this.matCd = matCd;
        this.matQty = matQty;
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

    public Double getMatQty() {
        return matQty;
    }

    public void setMatQty(Double matQty) {
        this.matQty = matQty;
    }
}
