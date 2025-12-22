package com.it.sps.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormApplicationDTO {
    // REMOVED: private String idNo; (Not needed here anymore)

    // Composite Key Fields
    private String applicationId;
    private String deptId;

    // Standard Fields
    private String applicationNo;
    private String applicationType;
    private String applicationSubType;
    private String status;
    private String description;

    // Date Fields
    private Date submitDate;
    private String addTime;

    // Workflow & Users
    private String preparedBy;

    // Contact Details
    private String contactIdNo;
    private String contactName;
    private String contactAddress;
    private String contactTelephone;
    private String contactMobile;
    private String contactEmail;
}