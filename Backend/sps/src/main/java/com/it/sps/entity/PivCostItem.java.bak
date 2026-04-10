package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "PIV_COST_ITEM")
public class PivCostItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGenCostItem")
    @SequenceGenerator(
        name = "SeqGenCostItem",
        sequenceName = "PIV_COST_ITEM_SEQ",
        allocationSize = 1
    )
    @Column(name = "PIV_COST_KEY")
    private Long pivCostKey;

    @Column(name = "AC_CD", nullable = false)
    private String acCd;

    @Column(name = "COST_ITEM_CODE", nullable = false)
    private String costItemCode;

    @Column(name = "APPLICATION_TYPE")
    private String applicationType;

    @Column(name = "APPLICATION_SUB_TYPE")
    private String applicationSubType;

    // Default constructor
    public PivCostItem() {
    }

    // Getters & Setters
    public Long getPivCostKey() {
        return pivCostKey;
    }

    public void setPivCostKey(Long pivCostKey) {
        this.pivCostKey = pivCostKey;
    }

    public String getAcCd() {
        return acCd;
    }

    public void setAcCd(String acCd) {
        this.acCd = acCd;
    }

    public String getCostItemCode() {
        return costItemCode;
    }

    public void setCostItemCode(String costItemCode) {
        this.costItemCode = costItemCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationSubType() {
        return applicationSubType;
    }

    public void setApplicationSubType(String applicationSubType) {
        this.applicationSubType = applicationSubType;
    }
}
