package com.it.sps.dto;

public class LoginBranchInfoDTO {
    private String deptId;
    private String deptType;
    private String deptTypeName;
    private String menuCode;
    private String menuName;
    private String activityCode;
    private String activityName;

    public LoginBranchInfoDTO(String deptId, String deptType, String deptTypeName, String menuCode, String menuName, String activityCode, String activityName) {
        this.deptId = deptId;
        this.deptType = deptType;
        this.deptTypeName = deptTypeName;
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.activityCode = activityCode;
        this.activityName = activityName;
    }

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }
    public String getDeptType() { return deptType; }
    public void setDeptType(String deptType) { this.deptType = deptType; }
    public String getDeptTypeName() { return deptTypeName; }
    public void setDeptTypeName(String deptTypeName) { this.deptTypeName = deptTypeName; }
    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }
    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public String getActivityCode() { return activityCode; }
    public void setActivityCode(String activityCode) { this.activityCode = activityCode; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
}