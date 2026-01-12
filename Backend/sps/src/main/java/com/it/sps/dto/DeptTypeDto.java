package com.it.sps.dto;

public class DeptTypeDto {
    private String deptTypeCode;
    private String name;

    public DeptTypeDto() {}

    public DeptTypeDto(String deptTypeCode, String name) {
        this.deptTypeCode = deptTypeCode;
        this.name = name;
    }

    public String getDeptTypeCode() { return deptTypeCode; }
    public void setDeptTypeCode(String deptTypeCode) { this.deptTypeCode = deptTypeCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
