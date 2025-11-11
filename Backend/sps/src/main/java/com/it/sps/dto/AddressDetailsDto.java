package com.it.sps.dto;

public class AddressDetailsDto {
    private String streetName;
    private String houseOrBuildingNo; // Optional - only if separated or parsed
    private String city;
    private String postalCode;

    private String area;
    private String nearestCSC;


    public AddressDetailsDto() {}

    public AddressDetailsDto(String streetName, String houseOrBuildingNo, String city, String postalCode) {
        this.streetName = streetName;
        this.houseOrBuildingNo = houseOrBuildingNo;
        this.city = city;
        this.postalCode = postalCode;


    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseOrBuildingNo() {
        return houseOrBuildingNo;
    }

    public void setHouseOrBuildingNo(String houseOrBuildingNo) {
        this.houseOrBuildingNo = houseOrBuildingNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNearestCSC() {
        return nearestCSC;
    }

    public void setNearestCSC(String nearestCSC) {
        this.nearestCSC = nearestCSC;
    }

}
