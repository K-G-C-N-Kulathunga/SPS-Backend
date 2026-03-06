package com.it.sps.dto;

import java.util.Date;

public class CostItemDTO {

    private String costItemCode;
    private String description;
    private Integer displayOrder;
    private Integer isPercent;
    private Integer isTotal;
    private String percentCalcItem;
    private String totalCalcItem;
//    private String flag;
    private String addUser;
    private Integer parentKey;   // fixed type
    private Date addedDate;      // added import
    private Date updDate;
    private String updUser;
    private Integer isActive;

    public Integer getIsActive() {return isActive;}

    public void setIsActive( Integer isActive){this.isActive =  isActive;}

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

//    public String getFlag() {
//        return flag;
//    }
//
//    public void setFlag(String flag) {
//        this.flag = flag;
//    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Integer getParentKey() {
        return parentKey;
    }

    public void setParentKey(Integer parentKey) {
        this.parentKey = parentKey;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}