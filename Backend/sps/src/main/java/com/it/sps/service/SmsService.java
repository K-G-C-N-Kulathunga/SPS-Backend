package com.it.sps.service;

public interface SmsService {
    void sendTempIdSms(String mobile, String tempId);
    void sendApplicationNoSms(String mobile, String applicationNo, String deptId);
}
