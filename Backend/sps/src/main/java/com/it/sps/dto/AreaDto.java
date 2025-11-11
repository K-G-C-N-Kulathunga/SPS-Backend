package com.it.sps.dto;

public class AreaDto {
    private String deptId;
    private String deptArea;

    public AreaDto(String deptId, String deptArea) {
        this.deptId = deptId;
        this.deptArea = deptArea;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptArea() {
        return deptArea;
    }

    public void setDeptArea(String deptArea) {
        this.deptArea = deptArea;
    }
}
