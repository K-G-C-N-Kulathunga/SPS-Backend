package com.it.sps.dto;

public class CostItemDTO {

    private String costItemCode;
    private String description;
    private Integer displayOrder;
    private Integer isPercent;
    private Integer isTotal;
    private String percentCalcItem;
    private String totalCalcItem;

    public String getCostItemCode() {
        return costItemCode;
    }

    public void setCostItemCode(String costItemCode) {
        this.costItemCode = costItemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getIsPercent() {
        return isPercent;
    }

    public void setIsPercent(Integer isPercent) {
        this.isPercent = isPercent;
    }

    public Integer getIsTotal() {
        return isTotal;
    }

    public void setIsTotal(Integer isTotal) {
        this.isTotal = isTotal;
    }

    public String getPercentCalcItem() {
        return percentCalcItem;
    }

    public void setPercentCalcItem(String percentCalcItem) {
        this.percentCalcItem = percentCalcItem;
    }

    public String getTotalCalcItem() {
        return totalCalcItem;
    }

    public void setTotalCalcItem(String totalCalcItem) {
        this.totalCalcItem = totalCalcItem;
    }
}
