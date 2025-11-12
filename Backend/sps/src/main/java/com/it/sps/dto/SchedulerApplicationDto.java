//package com.it.sps.dto;
//
//public class SchedulerApplicationDto {
//
//    private String applicationId;
//    private String deptId;
//    private String applicantName;
//    private String contactNumber;
//    private String serviceAddress;
//
//    // Getters and setters
//    public String getApplicationId() {
//        return applicationId;
//    }
//
//    public void setApplicationId(String applicationId) {
//        this.applicationId = applicationId;
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
//    public String getApplicantName() {
//        return applicantName;
//    }
//
//    public void setApplicantName(String applicantName) {
//        this.applicantName = applicantName;
//    }
//
//    public String getContactNumber() {
//        return contactNumber;
//    }
//
//    public void setContactNumber(String contactNumber) {
//        this.contactNumber = contactNumber;
//    }
//
//    public String getServiceAddress() {
//        return serviceAddress;
//    }
//
//    public void setServiceAddress(String serviceAddress) {
//        this.serviceAddress = serviceAddress;
//    }
//}

//package com.it.sps.dto;
//
//public class SchedulerApplicationDto {
//
//    private String applicationId;
//    private String deptId;
//
//    public SchedulerApplicationDto(String applicationId, String deptId) {
//        this.applicationId = applicationId;
//        this.deptId = deptId;
//    }
//
//    // Getters and setters
//    public String getApplicationId() {
//        return applicationId;
//    }
//
//    public void setApplicationId(String applicationId) {
//        this.applicationId = applicationId;
//    }
//
//    public String getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId(String deptId) {
//        this.deptId = deptId;
//    }
//}

package com.it.sps.dto;

public class SchedulerApplicationDto {

    private String applicationId;
    private String deptId;
    private String applicantName;
    private String contactNumber;
    private String serviceAddress;

    // Constructor for only applicationId and deptId (optional)
    public SchedulerApplicationDto(String applicationId, String deptId) {
        this.applicationId = applicationId;
        this.deptId = deptId;
    }

    // Default constructor
    public SchedulerApplicationDto() {}

    // Getters and setters
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}


