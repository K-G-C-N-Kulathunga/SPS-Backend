package com.it.sps.dto;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationDTO {

	private String applicationId;
    private String deptId;
    private String applicationNo;
    private String applicationType;
    private String applicationSubType;
    private Date submitDate;
    private String idNo;
    private String preparedBy;
    private String confirmedBy;
    private Date confirmedDate;
    private String confirmedTime;
    private String allocatedTo;
    private String allocatedBy;
    private Date allocatedDate;
    private String allocatedTime;
    private String status;
    private String addUser;
    private Date addDate;
    private String addTime;
    private String updUser;
    private Date updDate;
    private String updTime;
    private String description;
    private Date fromDate;
    private Date toDate;
    private Short durationInDays;
    private String durationType;
    private Short duration;
    private Short disconnectedWithin;
    private Short finalizedWithin;
    private String isLoanApp;
    private String isVisitngNeeded;
    private String samurdhiMember;
    private String contactIdNo;
    private String contactName;
    private String contactAddress;
    private String contactTelephone;
    private String contactMobile;
    private String contactEmail;
    private String isPiv1Needed;
    private String linkedWith;
    private String applicableStdYear;
    private String isTariffChange;
    private String isSequenceChange;
    private String existTariff;

}

