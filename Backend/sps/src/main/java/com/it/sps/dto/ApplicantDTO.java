package com.it.sps.dto;


import java.math.BigDecimal;
import java.util.Date;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ApplicantDTO {

    private String idNo;
    private String idType;
    private String personalCorporate;
    private String firstName;
    private String lastName;
    private String fullName;
    private String telephoneNo;
    private String email;
    private String mobileNo;
    private String city;
    private String suburb;
    private String streetAddress;
    private String postalCode;
    private String preferredLanguage;
    private String cebEmployee;
    private String status;
    private String addUser;
    private Date addDate;
    private String addTime;
    private String updUser;
    private Date updDate;
    private String updTime;
    private String entitledForLoan;
    private String memberOfSamurdhi;
    private String samurdhiId;
    private BigDecimal sharePrice;
    private BigDecimal noOfShares;
    private String loanReference;
    private BigDecimal loanAmount;
    private String companyName;
    private String deptId;
    

    public ApplicantDTO() {
    }

    public ApplicantDTO(String idNo, String idType, String personalCorporate, String firstName, String lastName, String fullName, String telephoneNo, String email, String mobileNo, String city, String suburb, String streetAddress, String postalCode, String preferredLanguage) {
        this.idNo = idNo;
        this.idType = idType;
        this.personalCorporate = personalCorporate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.telephoneNo = telephoneNo;
        this.email = email;
        this.mobileNo = mobileNo;
        this.city = city;
        this.suburb = suburb;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.preferredLanguage = preferredLanguage;
    }

    
}
