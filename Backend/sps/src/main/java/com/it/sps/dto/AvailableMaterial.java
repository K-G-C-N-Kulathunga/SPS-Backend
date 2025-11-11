package com.it.sps.dto;

import java.math.BigDecimal;

public class AvailableMaterial {
	
	public AvailableMaterial(String materialName, BigDecimal unitPrice, BigDecimal qtyOnHand, String majorUOM, String materialCode) {
		this.materialName = materialName;
		this.unitPrice = unitPrice;
		this.qtyOnHand = qtyOnHand;
        this.majorUOM = majorUOM;
        this.materialCode = materialCode;
	}
	
	private String materialName;
    private BigDecimal unitPrice;
    private BigDecimal qtyOnHand;
    private String majorUOM;
    private String materialCode;
    
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getQtyOnHand() {
		return qtyOnHand;
	}
	public void setQtyOnHand(BigDecimal qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}
	public String getMajorUOM() {
		return majorUOM;
	}

	public void setMajorUOM(String majorUOM) {
		this.majorUOM = majorUOM;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}


}
