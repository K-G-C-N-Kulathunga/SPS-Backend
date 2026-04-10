package com.it.sps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PIV_APPLICANT")
public class PivApplicant {

    @Id
    @Column(name = "PIV_NO", length = 24, nullable = false)
    private String pivNo;

    @Column(name = "DEPT_ID", length = 6)
    private String deptId;

    @Column(name = "ID_NO", length = 12)
    private String idNo;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "ADDRESS", length = 300, nullable = false)
    private String address;

    @Column(name = "TELEPHONE_NO", length = 50)
    private String telephoneNo;

    @Column(name = "EMAIL", length = 50)
    private String email;

    public String getPivNo() {
        return pivNo;
    }

    public void setPivNo(String pivNo) {
        this.pivNo = pivNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
