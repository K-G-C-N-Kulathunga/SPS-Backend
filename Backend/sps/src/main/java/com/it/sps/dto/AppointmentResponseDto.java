package com.it.sps.dto;

public class AppointmentResponseDto {
    private String appointmentId;
    private String deptId;
    private String applicationId;
    private String date;
    private String session;
    private String description;
    private String phone;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

//    public AppointmentResponseDto(String appointmentId, String deptId, String applicationId,
//                                  String date, String session, String description,
//                                  String phone, String name, String address) {
//        this.appointmentId = appointmentId;
//        this.deptId = deptId;
//        this.applicationId = applicationId;
//        this.date = date;
//        this.session = session;
//        this.description = description;
//        this.phone = phone;
//        this.name = name;
//        this.address = address;
//    }

    public AppointmentResponseDto(String deptId, String applicationId, String appointmentId,
                                  String date, String session, String description, String phone,
                                  String name, String address, Double latitude, Double longitude) {
        this.deptId = deptId;
        this.applicationId = applicationId;
        this.appointmentId = appointmentId;
        this.date = date;
        this.session = session;
        this.description = description;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getAppointmentId() { return appointmentId; }
    public String getDeptId() { return deptId; }
    public String getApplicationId() { return applicationId; }
    public String getDate() { return date; }
    public String getSession() { return session; }
    public String getDescription() { return description; }
    public String getPhone() { return phone; }
    public String getName() { return name; }
    public String getAddress() { return address; }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
