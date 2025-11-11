package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="APPLICATION_REFERENCE")
@NamedQuery(name="ApplicationReference.findAll", query="SELECT a FROM ApplicationReference a")
public class ApplicationReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ApplicationReferencePK id;

    @Column(name = "APPLICATION_NO", nullable = false, length = 21)
    private String applicationNo;  // Unique but not part of PK

    @Column(name = "STATUS", length = 2)
    private String status;

    @Column(name = "ID_NO", length = 12)
    private String idNo;

    @Column(name = "PROJECTNO", length = 21)
    private String projectNo;

    @Column(name = "CONSRUCTOR_ID", length = 4)
    private String constructorId;

    @Column(name = "POSTED_BY", length = 8)
    private String postedBy;

    @Column(name = "POSTED_DATE")
    private LocalDate postedDate;

    @Column(name = "POSTED_TIME", length = 11)
    private String postedTime;

    public ApplicationReferencePK getId() {
        return id;
    }

    public void setId(ApplicationReferencePK id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
}
