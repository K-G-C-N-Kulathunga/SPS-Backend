package com.it.sps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationFormRequestDTO {
    // We use this one just to get the ID
    private FormApplicantDTO formApplicantDto;

    private FormApplicationDTO applicationFormRequestDto;
    private FormWiringLandDetailDTO formWiringLandDetailDto;
}