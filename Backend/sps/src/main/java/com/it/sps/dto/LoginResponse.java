package com.it.sps.dto;

public class LoginResponse {
	
    private boolean success;
    private String rptUser;
    private String userLevel;
    private String message;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getRptUser() {
		return rptUser;
	}
	public void setRptUser(String rptUser) {
		this.rptUser = rptUser;
	}
	public String getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LoginResponse(boolean success, String rptUser, String userLevel, String message) {
		super();
		this.success = success;
		this.rptUser = rptUser;
		this.userLevel = userLevel;
		this.message = message;
	}
    
}
