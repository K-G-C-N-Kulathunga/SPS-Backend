package com.it.sps.dto;

import java.util.List;

public class DeptTaskAssignSaveRequest {
    private String userId;
    private String deptTypeCode;
    private List<DeptTaskAssignItemDto> items;

    public DeptTaskAssignSaveRequest() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDeptTypeCode() { return deptTypeCode; }
    public void setDeptTypeCode(String deptTypeCode) { this.deptTypeCode = deptTypeCode; }

    public List<DeptTaskAssignItemDto> getItems() { return items; }
    public void setItems(List<DeptTaskAssignItemDto> items) { this.items = items; }
}
