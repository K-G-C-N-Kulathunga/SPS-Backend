package com.it.sps.dto;

import java.util.List;

public class LoginResponseDTO {
    private String userLevel;
    private List<LoginBranchInfoDTO> branchInfoList;
    private String deptId;
    private String message;

    public LoginResponseDTO(String userLevel, List<LoginBranchInfoDTO> branchInfoList, String deptId, String message) {
        this.userLevel = userLevel;
        this.branchInfoList = branchInfoList;
        this.deptId = deptId;
        this.message = message;
    }

    public String getUserLevel() { return userLevel; }
    public void setUserLevel(String userLevel) { this.userLevel = userLevel; }
    public List<LoginBranchInfoDTO> getBranchInfoList() { return branchInfoList; }
    public void setBranchInfoList(List<LoginBranchInfoDTO> branchInfoList) { this.branchInfoList = branchInfoList; }
    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}