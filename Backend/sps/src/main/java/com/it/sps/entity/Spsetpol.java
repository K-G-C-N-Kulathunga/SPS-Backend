package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "SPSETPOL")
public class Spsetpol implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SpsetpolPK id;

    @Column(name = "MAT_QTY", precision = 6, scale = 2)
    private BigDecimal matQty;

    @Column(name = "JOB_CATEGORY_ID", length = 4)
    private String jobCategoryId;

    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    public Spsetpol() {
    }

    public SpsetpolPK getId() {
        return id;
    }

    public void setId(SpsetpolPK id) {
        this.id = id;
    }

    public BigDecimal getMatQty() {
        return matQty;
    }

    public void setMatQty(BigDecimal matQty) {
        this.matQty = matQty;
    }

    public String getJobCategoryId() {
        return jobCategoryId;
    }

    public void setJobCategoryId(String jobCategoryId) {
        this.jobCategoryId = jobCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
