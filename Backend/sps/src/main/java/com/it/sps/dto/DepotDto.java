package com.it.sps.dto;

public class DepotDto {
    private String deptId;
    private String deptFullName;

    public DepotDto(String deptId, String deptFullName) {
        this.deptId = deptId;
        this.deptFullName = deptFullName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptFullName() {
        return deptFullName;
    }

    public void setDeptFullName(String deptFullName) {
        this.deptFullName = deptFullName;
    }
}
