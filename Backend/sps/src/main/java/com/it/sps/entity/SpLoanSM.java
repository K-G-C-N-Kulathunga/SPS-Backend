package com.it.sps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "SPLOANSM")
public class SpLoanSM {

    @Id
    @Column(name = "LOAN_CODE", length = 1, nullable = false )
    private String loanCode; // CHAR(1) maps to String

    @Column(name = "LOAN_NAME", length = 30 )
    private String loanName;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom; // DATE maps best to LocalDate in modern Java

    @Column(name = "STATUS", length = 1 )
    private String status; // CHAR(1) MAPS TO STRING

    // NUMBER(5,2) -> PRECISION 5, SCALE 2. Use BidDecimal for financial data
    @Column(name = "INTEREST", precision = 5, scale = 2)
    private BigDecimal interest;

    @Column(name = "SORT_KEY")
    private Long sortKey;

    @Column(name = "LOAN_PERIOD")
    private Long longPeriod;

    @Column(name = "APPLICATION_TYPE", length = 2)
    private String applicationType;

    @Column(name = "APPLICATION_SUB_TYPE", length = 2)
    private String applicationSubType;
}