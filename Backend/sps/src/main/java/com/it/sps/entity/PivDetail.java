package com.it.sps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PIV_DETAIL")
@IdClass(PivDetailId.class)
public class PivDetail {

    @Id
    @Column(name = "PIV_NO", length = 255, nullable = false)
    private String pivNo;

    @Id
    @Column(name = "DEPT_ID", length = 6, nullable = false)
    private String deptId;

    @Column(name = "PIV_SEQ_NO", nullable = false)
    private BigDecimal pivSeqNo;

    @Column(name = "REFERENCE_TYPE", length = 3, nullable = false)
    private String referenceType;

    @Column(name = "REFERENCE_NO", length = 50, nullable = false)
    private String referenceNo;

    @Column(name = "ID_NO", length = 12, nullable = false)
    private String idNo;

    @Column(name = "PIV_AMOUNT")
    private BigDecimal pivAmount;

    @Column(name = "GRAND_TOTAL", nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "PREPARED_BY", length = 12, nullable = false)
    private String preparedBy;

    @Column(name = "STATUS", length = 2, nullable = false)
    private String status;

    @Column(name = "EST_REFERENCE_NO", length = 21)
    private String estReferenceNo;

    @Column(name = "TITLE_CD", length = 40)
    private String titleCd;

    @Column(name = "PIV_DATE")
    private Date pivDate;

    @Column(name = "ADD_USER", length = 12)
    private String addUser;

    @Column(name = "ADD_DATE")
    private Date addDate;

    public String getPivNo() { return pivNo; }
    public void setPivNo(String pivNo) { this.pivNo = pivNo; }

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public BigDecimal getPivSeqNo() { return pivSeqNo; }
    public void setPivSeqNo(BigDecimal pivSeqNo) { this.pivSeqNo = pivSeqNo; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getIdNo() { return idNo; }
    public void setIdNo(String idNo) { this.idNo = idNo; }

    public BigDecimal getPivAmount() { return pivAmount; }
    public void setPivAmount(BigDecimal pivAmount) { this.pivAmount = pivAmount; }

    public BigDecimal getGrandTotal() { return grandTotal; }
    public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }

    public String getPreparedBy() { return preparedBy; }
    public void setPreparedBy(String preparedBy) { this.preparedBy = preparedBy; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEstReferenceNo() { return estReferenceNo; }
    public void setEstReferenceNo(String estReferenceNo) { this.estReferenceNo = estReferenceNo; }

    public String getTitleCd() { return titleCd; }
    public void setTitleCd(String titleCd) { this.titleCd = titleCd; }

    public Date getPivDate() { return pivDate; }
    public void setPivDate(Date pivDate) { this.pivDate = pivDate; }

    public String getAddUser() { return addUser; }
    public void setAddUser(String addUser) { this.addUser = addUser; }

    public Date getAddDate() { return addDate; }
    public void setAddDate(Date addDate) { this.addDate = addDate; }
}
