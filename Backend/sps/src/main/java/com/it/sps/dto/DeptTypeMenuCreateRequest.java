package com.it.sps.dto;

public class DeptTypeMenuCreateRequest {

    private String deptTypeCode;
    private String menuCode;

    public DeptTypeMenuCreateRequest() {}

    public String getDeptTypeCode() {
        return deptTypeCode;
    }

    public void setDeptTypeCode(String deptTypeCode) {
        this.deptTypeCode = deptTypeCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}
