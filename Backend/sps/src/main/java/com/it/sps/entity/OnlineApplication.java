package com.it.sps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ONLINE_APPLICATION")
public class OnlineApplication {
    @Id
    @Column(name = "TEMP_ID", nullable = false, length = 24)
    private String tempId;

    @Column(name = "ID_NO", nullable = false, length = 12)
    private String idNo;

    @Column(name = "ID_TYPE", nullable = false, length = 3)
    private String idType;

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Column(name = "FULL_NAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "STREET_ADDRESS", nullable = false, length = 100)
    private String streetAddress;

    @Column(name = "SUBURB", nullable = false, length = 100)
    private String suburb;

    @Column(name = "CITY", length = 100)
    private String city;

    @Column(name = "POSTAL_CODE", length = 5)
    private String postalCode;

    @Column(name = "EMAIL", nullable = false, length = 30)
    private String email;

    @Column(name = "TELEPHONE", nullable = false, length = 10)
    private String telephone;

    @Column(name = "MOBILE", nullable = false, length = 10)
    private String mobile;

    @Column(name = "CONTACT_ID_NO", nullable = false, length = 12)
    private String contactIdNo;

    @Column(name = "CONTACT_NAME", nullable = false, length = 100)
    private String contactName;

    @Column(name = "CONTACT_ADDRESS", nullable = false, length = 150)
    private String contactAddress;

    @Column(name = "CONTACT_TELEPHONE", nullable = false, length = 10)
    private String contactTelephone;

    @Column(name = "CONTACT_MOBILE", nullable = false, length = 10)
    private String contactMobile;

    @Column(name = "CONTACT_EMAIL", nullable = false, length = 30)
    private String contactEmail;

    @Column(name = "SERVICE_STREET_ADDRESS", nullable = false, length = 100)
    private String serviceStreetAddress;

    @Column(name = "SERVICE_SUBURB", nullable = false, length = 100)
    private String serviceSuburb;

    @Column(name = "SERVICE_CITY", length = 100)
    private String serviceCity;

    @Column(name = "SERVICE_POSTAL_CODE", length = 5)
    private String servicePostalCode;

    @Column(name = "ASSESSMENT_NO", length = 6)
    private String assessmentNo;

    @Column(name = "NEIGHBOURS_ACC_NO", length = 10)
    private String neighboursAccNo;

    @Column(name = "DEPT_ID", nullable = false, length = 6)
    private String deptId;

    @Column(name = "PHASE", nullable = false)
    private Boolean phase = false;

    @Column(name = "CONNECTION_TYPE", nullable = false)
    private Short connectionType;

    @Column(name = "TARIFF_CAT_CODE", nullable = false, length = 2)
    private String tariffCatCode;

    @Column(name = "NO_OF_BULBS")
    private Short noOfBulbs;

    @Column(name = "NO_OF_FANS")
    private Short noOfFans;

    @Column(name = "NO_OF_PLUGS_5A")
    private Short noOfPlugs5a;

    @Column(name = "NO_OF_PLUGS_15A")
    private Short noOfPlugs15a;

    @Column(name = "MOTOR_TOTAL")
    private Short motorTotal;

    @Column(name = "WELDING_PLANT")
    private Short weldingPlant;

    @Column(name = "METAL_CRUSHER")
    private Short metalCrusher;

    @Column(name = "SAW_MILL")
    private Short sawMill;

    @Column(name = "APPLICATION_DATE")
    private Date applicationDate;

    @Column(name = "IS_TRANSFERED", nullable = false)
    private Boolean isTransfered = false;

    @Column(name = "PREFERRED_LANGUAGE", nullable = false, length = 2)
    private String preferredLanguage;

    @Column(name = "PERSONAL_CORPORATE", length = 3)
    private String personalCorporate;

    @Column(name = "USAGE_ELECTRICITY", length = 50)
    private String usageElectricity;

    @Column(name = "REQUESTING_TIME", length = 50)
    private String requestingTime;

    @Column(name = "BOUNDARY_WALL", length = 5)
    private String boundaryWall;

    @Column(name = "PRE_ACCOUNT_NO", length = 20)
    private String preAccountNo;

    @Column(name = "OWNERSHIP" , length = 10)
    private String ownership;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;


}