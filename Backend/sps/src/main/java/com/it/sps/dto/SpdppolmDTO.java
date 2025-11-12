package com.it.sps.dto;

public class SpdppolmDTO {

    private String deptId; // Maps to DEPT_ID
    private String matCd; // Maps to MAT_CD
    private String isActive; // Maps to IS_ACTIVE (Y/N or 1/0)

    public SpdppolmDTO() {
    }

    public SpdppolmDTO(String deptId, String matCd, String isActive) {
        this.deptId = deptId;
        this.matCd = matCd;
        this.isActive = isActive;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
