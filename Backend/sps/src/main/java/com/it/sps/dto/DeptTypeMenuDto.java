package com.it.sps.dto;

public class DeptTypeMenuDto {
    private String deptTypeCode;
    private String menuCode;

    // Optional extra fields if you want to show menu data
    private String menuDisplayName;

    public DeptTypeMenuDto() {}

    public DeptTypeMenuDto(String deptTypeCode, String menuCode, String menuDisplayName) {
        this.deptTypeCode = deptTypeCode;
        this.menuCode = menuCode;
        this.menuDisplayName = menuDisplayName;
    }

    public String getDeptTypeCode() { return deptTypeCode; }
    public void setDeptTypeCode(String deptTypeCode) { this.deptTypeCode = deptTypeCode; }

    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }

    public String getMenuDisplayName() { return menuDisplayName; }
    public void setMenuDisplayName(String menuDisplayName) { this.menuDisplayName = menuDisplayName; }
}
