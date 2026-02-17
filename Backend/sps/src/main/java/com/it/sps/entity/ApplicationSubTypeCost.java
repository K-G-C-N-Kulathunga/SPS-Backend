package com.it.sps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "APPLICATION_SUB_TYPE_COST", schema = "DACONS16")
@IdClass(ApplicationSubTypeCostId.class)
public class ApplicationSubTypeCost {

    @Id
    @Column(name = "APPLICATION_TYPE", length = 2)
    private String applicationType;

    @Id
    @Column(name = "APPLICATION_SUB_TYPE", length = 2)
    private String applicationSubType;

    @Id
    @Column(name = "COST_ITEM_CODE", length = 20)
    private String costItemCode;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    @Column(name = "TOTAL_CALC_ITEM", length = 20)
    private String totalCalcItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "COST_ITEM_CODE",
            referencedColumnName = "COST_ITEM_CODE",
            insertable = false,
            updatable = false
    )
    private CostItem costItem;

    // getters & setters

}
