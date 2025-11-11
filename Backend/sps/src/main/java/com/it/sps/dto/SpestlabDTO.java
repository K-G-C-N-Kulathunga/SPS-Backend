package com.it.sps.dto;

import java.math.BigDecimal;

public class SpestlabDTO {
	
	private String estimateNo;
	private String deptId;
	private String labourCode;
	private String activityDescription;
	private BigDecimal labourCost;
	
	public BigDecimal getLabourCost() {
		return labourCost;
	}
	public void setLabourCost(BigDecimal labourCost) {
		this.labourCost = labourCost;
	}
	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getLabourCode() {
		return labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	
	

}
