package com.it.sps.dto;

import java.math.BigDecimal;

public class PcestdttDTO {
	
	private String estimateNo;
	private int revNo;
	private String deptId;
	private String resCd;
	
	private BigDecimal estimateQty;
	private BigDecimal estimateCost;
	
	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public int getRevNo() {
		return revNo;
	}
	public void setRevNo(int revNo) {
		this.revNo = revNo;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getResCd() {
		return resCd;
	}
	public void setResCd(String resCd) {
		this.resCd = resCd;
	}
	public BigDecimal getEstimateQty() {
		return estimateQty;
	}
	public void setEstimateQty(BigDecimal estimateQty) {
		this.estimateQty = estimateQty;
	}
	public BigDecimal getEstimateCost() {
		return estimateCost;
	}
	public void setEstimateCost(BigDecimal estimateCost) {
		this.estimateCost = estimateCost;
	}
	
	

}
