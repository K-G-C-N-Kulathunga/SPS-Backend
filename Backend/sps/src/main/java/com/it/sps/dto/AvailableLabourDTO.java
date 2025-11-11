package com.it.sps.dto;

import java.math.BigDecimal;

public class AvailableLabourDTO {

	private String labCode;
	//private String year;
	private String name;
	private BigDecimal unitPrice;
	private BigDecimal hours;

	public AvailableLabourDTO(String labourCode, String labourName, BigDecimal unitPrice, BigDecimal labourHours) {
		super();
		this.labCode = labourCode;
//		this.year = year;
		this.name = labourName;
		this.unitPrice = unitPrice;
		this.hours = labourHours;
	}

	public String getLabCode() {
		return labCode;
	}

	public void setLabCode(String labCode) {
		this.labCode = labCode;
	}
	//
	//public String getYear() {
//		return year;
	//}
	//
	//public void setYear(String year) {
//		this.year = year;
	//}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}


	

}
