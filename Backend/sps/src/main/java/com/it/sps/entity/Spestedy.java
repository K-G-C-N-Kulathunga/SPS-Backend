package com.it.sps.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "SPESTEDY")
@IdClass(SpestedyId.class)
public class Spestedy {

    @Id
    @Column(name = "APPOINTMENT_ID", length = 12)
    private String appointmentId;

    @Id
    @Column(name = "DEPT_ID", length = 6)
    private String deptId;

    @Column(name = "APPOINMENT_TYPE", length = 10)
    private String appointmentType;

    @Column(name = "REFERENCE_NO", length = 20)
    private String referenceNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "APPOINTMENT_DATE")
    private Date appointmentDate;

    @Column(name = "TIME_SESSION", length = 10)
    private String timeSession;

    @Column(name = "SUBURB", length = 50)
    private String suburb;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "ALLOCATED_BY", length = 10)
    private String allocatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "ALLOCATED_DATE")
    private Date allocatedDate;

    @Column(name = "ALLOCATED_TIME", length = 11)
    private String allocatedTime;

    @Column(name = "ALLOCATED_TO", length = 10)
    private String allocatedTo;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    // ------------------- Foreign Key Relationships ----------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "REFERENCE_NO", referencedColumnName = "APPLICATION_ID", insertable = false, updatable = false),
            @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEPT_ID", insertable = false, updatable = false)
    })
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "REFERENCE_NO", referencedColumnName = "APPLICATION_ID", insertable = false, updatable = false),
            @JoinColumn(name = "DEPT_ID", referencedColumnName = "DEPT_ID", insertable = false, updatable = false)
    })
    private WiringLandDetail wiringLandDetail;

    // ------------------- Setters ----------------------

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setTimeSession(String timeSession) {
        this.timeSession = timeSession;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAllocatedBy(String allocatedBy) {
        this.allocatedBy = allocatedBy;
    }

    public void setAllocatedDate(Date allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    public void setAllocatedTime(String allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public void setAllocatedTo(String allocatedTo) {
        this.allocatedTo = allocatedTo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ------------------- Getters ----------------------

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getTimeSession() {
        return timeSession;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getStatus() {
        return status;
    }

    public String getAllocatedBy() {
        return allocatedBy;
    }

    public Date getAllocatedDate() {
        return allocatedDate;
    }

    public String getAllocatedTime() {
        return allocatedTime;
    }

    public String getAllocatedTo() {
        return allocatedTo;
    }

    public String getDescription() {
        return description;
    }

    public Application getApplication() {
        return application;
    }

    public WiringLandDetail getWiringLandDetail() {
        return wiringLandDetail;
    }
}
