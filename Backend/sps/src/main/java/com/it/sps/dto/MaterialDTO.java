package com.it.sps.dto;

import java.math.BigDecimal;

public class MaterialDTO {
    private String materialName;
    private BigDecimal unitPrice;
    private String majorUOM;
    private String materialCode;

    public MaterialDTO(String materialName, BigDecimal unitPrice, String majorUOM, String materialCode) {
        this.materialName = materialName;
        this.unitPrice = unitPrice;
        this.majorUOM = majorUOM;
        this.materialCode = materialCode;
    }

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
