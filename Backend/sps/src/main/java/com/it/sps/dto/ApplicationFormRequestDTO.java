package com.it.sps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFormRequestDTO {
    // Match the names used in service code
    private ApplicationDTO applicationDto;  // Changed from FormApplicationDTO
    private ApplicantDTO applicantDto;      // Changed from FormApplicantDTO
    private WiringLandDetailDto wiringLandDetailDto;  // Changed from FormWiringLandDetailDTO
}