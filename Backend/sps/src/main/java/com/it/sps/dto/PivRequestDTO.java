package com.it.sps.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class PivRequestDTO {

    private String estimationNo;

    private String pivType;

    private BigDecimal totalAmount;

    private ApplicantDTO applicant;

    private List<PivChargeDto> charges;

}