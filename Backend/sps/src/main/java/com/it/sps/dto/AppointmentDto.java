//package com.it.sps.dto;
//
//import lombok.Data;
//
//@Data
//public class AppointmentDto {
//    private String appointmentId;
//    private String deptId;
//    private String appointmentType;
//    private String referenceNo;
//    private String appointmentDate; // or use LocalDate if parsing manually
//    private String timeSession;
//    private String suburb;
//    private String status;
//    private String allocatedBy;
//    private String allocatedDate;
//    private String allocatedTime;
//    private String allocatedTo;
//    private String description;
//
//    public String getAppointmentId() {
//        return appointmentId;
//    }
//
//    public void setAppointmentId(String appointmentId) {
//        this.appointmentId = appointmentId;
//    }
//
//    public String getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId(String deptId) {
//        this.deptId = deptId;
//    }
//
//    public String getAppointmentType() {
//        return appointmentType;
//    }
//
//    public void setAppointmentType(String appointmentType) {
//        this.appointmentType = appointmentType;
//    }
//
//    public String getReferenceNo() {
//        return referenceNo;
//    }
//
//    public void setReferenceNo(String referenceNo) {
//        this.referenceNo = referenceNo;
//    }
//
//    public String getAppointmentDate() {
//        return appointmentDate;
//    }
//
//    public void setAppointmentDate(String appointmentDate) {
//        this.appointmentDate = appointmentDate;
//    }
//
//    public String getTimeSession() {
//        return timeSession;
//    }
//
//    public void setTimeSession(String timeSession) {
//        this.timeSession = timeSession;
//    }
//
//    public String getSuburb() {
//        return suburb;
//    }
//
//    public void setSuburb(String suburb) {
//        this.suburb = suburb;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getAllocatedBy() {
//        return allocatedBy;
//    }
//
//    public void setAllocatedBy(String allocatedBy) {
//        this.allocatedBy = allocatedBy;
//    }
//
//    public String getAllocatedDate() {
//        return allocatedDate;
//    }
//
//    public void setAllocatedDate(String allocatedDate) {
//        this.allocatedDate = allocatedDate;
//    }
//
//    public String getAllocatedTime() {
//        return allocatedTime;
//    }
//
//    public void setAllocatedTime(String allocatedTime) {
//        this.allocatedTime = allocatedTime;
//    }
//
//    public String getAllocatedTo() {
//        return allocatedTo;
//    }
//
//    public void setAllocatedTo(String allocatedTo) {
//        this.allocatedTo = allocatedTo;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}


package com.it.sps.dto;

import java.util.Date;

public class AppointmentDto {

    private String appointmentId;
    private String deptId;
    private String appointmentType;  // e.g. "Inspection"
    private String referenceNo;      // e.g. applicationId
    private Date appointmentDate;
    private String timeSession;
    private String suburb;
    private String allocatedBy;
    private String allocatedTo;
    private String description;

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

    public String getAllocatedBy() {
        return allocatedBy;
    }

    public String getAllocatedTo() {
        return allocatedTo;
    }

    public String getDescription() {
        return description;
    }

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

    public void setAllocatedBy(String allocatedBy) {
        this.allocatedBy = allocatedBy;
    }

    public void setAllocatedTo(String allocatedTo) {
        this.allocatedTo = allocatedTo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}



