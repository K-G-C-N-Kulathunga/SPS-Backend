package com.it.sps.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public void sendApplicationNoSms(String mobileNo, String applicationNo, String deptId) {
        // Logic to send SMS goes here.
        // For now, we print to console so the build passes.
        System.out.println(">> SMS SENT to " + mobileNo + ": Your App No is " + applicationNo);
    }
}