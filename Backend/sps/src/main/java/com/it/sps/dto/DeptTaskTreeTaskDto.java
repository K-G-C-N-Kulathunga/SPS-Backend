package com.it.sps.dto;

public class DeptTaskTreeTaskDto {
    private String activityCode;
    private String activityName;
    private String page;
    private Integer orderKey;
    private boolean assigned;

    public DeptTaskTreeTaskDto() {}

    public String getActivityCode() { return activityCode; }
    public void setActivityCode(String activityCode) { this.activityCode = activityCode; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }

    public Integer getOrderKey() { return orderKey; }
    public void setOrderKey(Integer orderKey) { this.orderKey = orderKey; }

    public boolean isAssigned() { return assigned; }
    public void setAssigned(boolean assigned) { this.assigned = assigned; }
}
