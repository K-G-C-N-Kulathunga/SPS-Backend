package com.it.sps.dto;

public class TaskUserCategoryDTO {
    private String userRoleCode;
    private String menuCode;
    private String activityCode;

    public TaskUserCategoryDTO() {}

    public TaskUserCategoryDTO(String userRoleCode, String menuCode, String activityCode) {
        this.userRoleCode = userRoleCode;
        this.menuCode = menuCode;
        this.activityCode = activityCode;
    }

    public String getUserRoleCode() { return userRoleCode; }
    public void setUserRoleCode(String userRoleCode) { this.userRoleCode = userRoleCode; }

    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }

    public String getActivityCode() { return activityCode; }
    public void setActivityCode(String activityCode) { this.activityCode = activityCode; }
}
