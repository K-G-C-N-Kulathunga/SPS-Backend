package com.it.sps.dto;

public class DeptTypeCreateRequest {

    private String deptTypeCode;
    private String name;

    public DeptTypeCreateRequest() {}

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
