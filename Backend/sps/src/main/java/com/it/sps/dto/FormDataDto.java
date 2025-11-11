package com.it.sps.dto;


public class FormDataDto {


    private ApplicationDTO applicationDto;
    private WiringLandDetailDto wiringLandDetailDto;
    private ApplicantDTO applicantDto;
    private ApplicationReferenceDto applicationReferenceDto;

    public ApplicationDTO getApplicationDto() {
        return applicationDto;
    }

    public void setApplicationDto(ApplicationDTO applicationDto) {
        this.applicationDto = applicationDto;
    }

    public WiringLandDetailDto getWiringLandDetailDto() {
        return wiringLandDetailDto;
    }

    public void setWiringLandDetailDto(WiringLandDetailDto wiringLandDetailDto) {
        this.wiringLandDetailDto = wiringLandDetailDto;
    }

    public ApplicantDTO getApplicantDto() {
        return applicantDto;
    }

    public void setApplicantDto(ApplicantDTO applicantDto) {
        this.applicantDto = applicantDto;
    }

    public ApplicationReferenceDto getApplicationReferenceDto() {
        return applicationReferenceDto;
    }

    public void setApplicationReferenceDto(ApplicationReferenceDto applicationReferenceDto) {
        this.applicationReferenceDto = applicationReferenceDto;
    }
}
