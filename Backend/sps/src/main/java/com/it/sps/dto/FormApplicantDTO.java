package com.it.sps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormApplicantDTO {
    // This matches the frontend 'formApplicantDto' fields
    private String idNo;
    private String idType;
    private String personalCorporate;
    private String firstName;
    private String lastName;
    private String fullName;
    private String streetAddress;
    private String suburb;
    private String city;
    private String postalCode;
    private String telephoneNo;
    private String mobileNo;
    private String email;
    private String preferredLanguage;
    private String appSubType;
    private String loanType;
    private String customerDescription;
}