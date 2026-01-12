package com.it.sps.dto;

import java.util.List;

public class DeptTaskTreeDto {
    private String menuCode;
    private String displayName;
    private Integer orderKey;
    private List<DeptTaskTreeTaskDto> tasks;

    public DeptTaskTreeDto() {}

    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Integer getOrderKey() { return orderKey; }
    public void setOrderKey(Integer orderKey) { this.orderKey = orderKey; }

    public List<DeptTaskTreeTaskDto> getTasks() { return tasks; }
    public void setTasks(List<DeptTaskTreeTaskDto> tasks) { this.tasks = tasks; }
}
