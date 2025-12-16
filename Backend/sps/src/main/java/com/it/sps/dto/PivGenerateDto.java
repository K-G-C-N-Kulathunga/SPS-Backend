package com.it.sps.dto;

public class PivGenerateDto {

    private String estimationNo;
    private String customerId;
    private String customerName;
    private String address;
    private String telephone;
    private String mobile;
    private String email;

    public PivGenerateDto() {
    }

    public PivGenerateDto(String estimationNo, String customerId, String customerName, String address,
                          String telephone, String mobile, String email) {
        this.estimationNo = estimationNo;
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.telephone = telephone;
        this.mobile = mobile;
        this.email = email;
    }

    public String getEstimationNo() {
        return estimationNo;
    }

    public void setEstimationNo(String estimationNo) {
        this.estimationNo = estimationNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
