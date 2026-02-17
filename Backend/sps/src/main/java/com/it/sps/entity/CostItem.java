package com.it.sps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "COST_ITEM", schema = "DACONS16")
public class CostItem {

    @Id
    @Column(name = "COST_ITEM_CODE", nullable = false, length = 20)
    private String costItemCode;

    @Column(name = "DESCRIPTION", nullable = false, length = 200)
    private String description;

    @Column(name = "ORDER_KEY", precision = 4, scale = 0)
    private Integer orderKey;

    // NUMBER(1,0) with CHECK(0,1) – map as Boolean
    @Column(name = "IS_PERCENT", nullable = false)
    private Boolean isPercent;

    @Column(name = "IS_TOTAL", nullable = false)
    private Boolean isTotal;

    @Column(name = "Percent_Calc_Item", length = 20)
    private String percentCalcItem;

    // Optional helpers for UI (0/1 → Y/N)
    @Transient
    public String getIsPercentYn() {
        return Boolean.TRUE.equals(isPercent) ? "Y" : "N";
    }

    @Transient
    public String getIsTotalYn() {
        return Boolean.TRUE.equals(isTotal) ? "Y" : "N";
    }
}
