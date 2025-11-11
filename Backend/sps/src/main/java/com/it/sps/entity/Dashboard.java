package com.it.sps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "DASHBOARD")
public class Dashboard {
    @Id
    @Column(name = "APPLICATION_ID", nullable = false, length = 18)
    private String applicationId;

    @Column(name = "DEPT_ID", nullable = false, length = 6)
    private String deptId;

    @Column(name = "ID_NO", nullable = false, length = 12)
    private String idNo;

    @Column(name = "CONSUMER_NAME", nullable = false, length = 111)
    private String consumerName;

    @Column(name = "CONSUMER_ADDRESS", nullable = false, length = 100)
    private String consumerAddress;

    @Column(name = "ESTIMATE_NO", length = 18)
    private String estimateNo;

    @Column(name = "ESTIMATE_NO_CHAR", length = 18)
    private String estimateNoChar;

    @Column(name = "APPLICATION_TYPE", nullable = false, length = 2)
    private String applicationType;

    @Column(name = "APPLICATION_SUB_TYPE", nullable = false, length = 2)
    private String applicationSubType;

    @Column(name = "LOAN_TYPE", nullable = false, length = 1)
    private String loanType;

    @Column(name = "EXISTING_ACC_NO", length = 10)
    private String existingAccNo;

    @Column(name = "PHASE", nullable = false)
    private Boolean phase = false;

    @Column(name = "CONNECTION_TYPE", nullable = false)
    private Short connectionType;

    @Column(name = "TARIFF_CAT_CODE", nullable = false, length = 2)
    private String tariffCatCode;

    @Column(name = "TARIFF_CODE", nullable = false, length = 2)
    private String tariffCode;

    @Column(name = "APP_SUBMITTED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date appSubmittedDate;

    @Column(name = "APP_SUBMITTED_BY", nullable = false, length = 12)
    private String appSubmittedBy;

    @Column(name = "STATUS", nullable = false, length = 3)
    private String status;

    @Column(name = "STATUS_CHANGED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusChangedDate;

    @Column(name = "STATUS_CHANGED_BY", nullable = false, length = 12)
    private String statusChangedBy;

    @Column(name = "STATUS_CHANGED_REASON", nullable = false, length = 150)
    private String statusChangedReason;

    @Column(name = "PIV1_NO", length = 22)
    private String piv1No;

    @Column(name = "PIV1_AMOUNT", precision = 8, scale = 2)
    private BigDecimal piv1Amount;

    @Column(name = "PIV1_ISSUED_DATE")
    private Instant piv1IssuedDate;

    @Column(name = "PIV1_ISSUED_BY", length = 12)
    private String piv1IssuedBy;

    @Column(name = "PIV1_PAID_DATE")
    private Instant piv1PaidDate;

    @Column(name = "PIV1_COMFIRMED_DATE")
    private Instant piv1ComfirmedDate;

    @Column(name = "PIV1_COMFIRMED_BY", length = 12)
    private String piv1ComfirmedBy;

    @Column(name = "APPOINTMENT_DATE")
    private Instant appointmentDate;

    @Column(name = "APPOINTMENT_BY", length = 12)
    private String appointmentBy;

    @Column(name = "PIV_RIP_NO", length = 22)
    private String pivRipNo;

    @Column(name = "PIV_RIP_AMOUNT", precision = 8, scale = 2)
    private BigDecimal pivRipAmount;

    @Column(name = "PIV_RIP_ISSUED_DATE")
    private Instant pivRipIssuedDate;

    @Column(name = "PIV_RIP_ISSUED_BY", length = 12)
    private String pivRipIssuedBy;

    @Column(name = "PIV_RIP_PAID_DATE")
    private Instant pivRipPaidDate;

    @Column(name = "PIV_RIP_COMFIRMED_DATE")
    private Instant pivRipComfirmedDate;

    @Column(name = "PIV_RIP_COMFIRMED_BY", length = 12)
    private String pivRipComfirmedBy;

    @Column(name = "LOCATION_VISITED_DATE")
    private Instant locationVisitedDate;

    @Column(name = "LOCATION_VISITED_BY", length = 12)
    private String locationVisitedBy;

    @Column(name = "ESTIMATED_DATE")
    private Instant estimatedDate;

    @Column(name = "ESTIMATED_BY", length = 12)
    private String estimatedBy;

    @Column(name = "STD_COST", precision = 12, scale = 2)
    private BigDecimal stdCost;

    @Column(name = "DETAILED_COST", precision = 12, scale = 2)
    private BigDecimal detailedCost;

    @Column(name = "ESTIMATE_APPROVED_DATE")
    private Instant estimateApprovedDate;

    @Column(name = "ESTIMATE_APPROVED_BY", length = 12)
    private String estimateApprovedBy;

    @Column(name = "PIV2_NO", length = 22)
    private String piv2No;

    @Column(name = "PIV2_AMOUNT", precision = 12, scale = 2)
    private BigDecimal piv2Amount;

    @Column(name = "PIV2_ISSUED_DATE")
    private Instant piv2IssuedDate;

    @Column(name = "PIV2_ISSUED_BY", length = 12)
    private String piv2IssuedBy;

    @Column(name = "PIV2_PAID_DATE")
    private Instant piv2PaidDate;

    @Column(name = "PIV2_COMFIRMED_DATE")
    private Instant piv2ComfirmedDate;

    @Column(name = "PIV2_COMFIRMED_BY", length = 12)
    private String piv2ComfirmedBy;

    @Column(name = "PIV2_ESD_NO", length = 22)
    private String piv2EsdNo;

    @Column(name = "PIV2_ESD_AMOUNT", precision = 12, scale = 2)
    private BigDecimal piv2EsdAmount;

    @Column(name = "PIV2_ESD_ISSUED_DATE")
    private Instant piv2EsdIssuedDate;

    @Column(name = "PIV2_ESD_ISSUED_BY", length = 12)
    private String piv2EsdIssuedBy;

    @Column(name = "PIV2_ESD_PAID_DATE")
    private Instant piv2EsdPaidDate;

    @Column(name = "PIV2_ESD_COMFIRMED_DATE")
    private Instant piv2EsdComfirmedDate;

    @Column(name = "PIV2_ESD_COMFIRMED_BY", length = 12)
    private String piv2EsdComfirmedBy;

    @Column(name = "PIV2_ELN_NO", length = 22)
    private String piv2ElnNo;

    @Column(name = "PIV2_ELN_AMOUNT", precision = 12, scale = 2)
    private BigDecimal piv2ElnAmount;

    @Column(name = "PIV2_ELN_ISSUED_DATE")
    private Instant piv2ElnIssuedDate;

    @Column(name = "PIV2_ELN_ISSUED_BY", length = 12)
    private String piv2ElnIssuedBy;

    @Column(name = "PIV2_ELN_PAID_DATE")
    private Instant piv2ElnPaidDate;

    @Column(name = "PIV2_ELN_COMFIRMED_DATE")
    private Instant piv2ElnComfirmedDate;

    @Column(name = "PIV2_ELN_COMFIRMED_BY", length = 12)
    private String piv2ElnComfirmedBy;

    @Column(name = "JOB_NO", length = 20)
    private String jobNo;

    @Column(name = "JOB_CREATED_DATE")
    private Instant jobCreatedDate;

    @Column(name = "JOB_CREATED_BY", length = 12)
    private String jobCreatedBy;

    @Column(name = "CONTRACTOR_ID", length = 6)
    private String contractorId;

    @Column(name = "CONTRACTOR_NAME", length = 50)
    private String contractorName;

    @Column(name = "JOB_ALLOCATED_DATE")
    private Instant jobAllocatedDate;

    @Column(name = "JOB_ALLOCATED_BY", length = 12)
    private String jobAllocatedBy;

    @Column(name = "JOB_APPROVED_DATE")
    private Instant jobApprovedDate;

    @Column(name = "JOB_APPROVED_BY", length = 12)
    private String jobApprovedBy;

    @Column(name = "PIV3_NO", length = 22)
    private String piv3No;

    @Column(name = "PIV3_AMOUNT", precision = 12, scale = 2)
    private BigDecimal piv3Amount;

    @Column(name = "PIV3_ISSUED_DATE")
    private Instant piv3IssuedDate;

    @Column(name = "PIV3_ISSUED_BY", length = 12)
    private String piv3IssuedBy;

    @Column(name = "PIV3_PAID_DATE")
    private Instant piv3PaidDate;

    @Column(name = "PIV3_COMFIRMED_DATE")
    private Instant piv3ComfirmedDate;

    @Column(name = "PIV3_COMFIRMED_BY", length = 12)
    private String piv3ComfirmedBy;

    @Column(name = "JOB_FINISHED_DATE")
    private Instant jobFinishedDate;

    @Column(name = "JOB_FINISHED_BY", length = 12)
    private String jobFinishedBy;

    @Column(name = "BILL_CREATED_DATE")
    private Instant billCreatedDate;

    @Column(name = "BILL_CREATED_BY", length = 12)
    private String billCreatedBy;

    @Column(name = "JOB_EXPORTED_DATE")
    private Instant jobExportedDate;

    @Column(name = "JOB_EXPORTED_BY", length = 12)
    private String jobExportedBy;

    @Column(name = "SOFT_CLOSED_DATE")
    private Instant softClosedDate;

    @Column(name = "SOFT_CLOSED_BY", length = 12)
    private String softClosedBy;

    @Column(name = "HARD_CLOSED_DATE")
    private Instant hardClosedDate;

    @Column(name = "HARD_CLOSED_BY", length = 12)
    private String hardClosedBy;

    @Column(name = "ACCOUNT_NO", length = 10)
    private String accountNo;

    @Column(name = "ACC_OPENED_DATE")
    private Instant accOpenedDate;

    @Column(name = "ACC_OPENED_BY", length = 12)
    private String accOpenedBy;

    @Column(name = "ORIGINATED_BY", nullable = false, length = 3)
    private String originatedBy;

    @Column(name = "JOB_DEPT_ID", length = 6)
    private String jobDeptId;

    @Column(name = "SMC_STATUS", length = 3)
    private String smcStatus;

    @Column(name = "ONLINE_APP_NO", length = 20)
    private String onlineAppNo;

}