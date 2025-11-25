package com.it.sps.dto;

public class TaskDTO {
    private String menuCode;
    private String activityCode;
    private String activityName;
    private String page;

    public TaskDTO(String menuCode, String activityCode, String activityName, String page) {
        this.menuCode = menuCode;
        this.activityCode = activityCode;
        this.activityName = activityName;
        this.page = page;
    }

    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }
    public String getActivityCode() { return activityCode; }
    public void setActivityCode(String activityCode) { this.activityCode = activityCode; }
    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }
    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }
}