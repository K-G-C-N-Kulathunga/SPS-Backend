package com.it.sps.service;

import com.it.sps.dto.SmsDataProjectCosting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
    private final RestTemplate restTemplate;

    @Value("${sms.api.enabled:true}")  private boolean smsEnabled;
    @Value("${sms.api.url:http://10.128.1.126/SMSServiceJobCosting/api/SaveSMSDetails/SaveSMSDetailsProjectCosting}")
    private String smsApiUrl;
    @Value("${sms.api.appkey:ONLINEAPP}") private String appKey;
    @Value("${sms.api.alias:CEB Serves}") private String alias;
    @Value("${sms.api.procFlag:W}")       private String procFlag;
    @Value("${sms.api.defaultMessageTemplate:Your CEB Temporary ID is %s. Use this for future reference.}")
    private String messageTemplate;

    @Value("${sms.api.applicationMessageTemplate:Your CEB Application No is %s. Please keep it for future reference.}")
    private String applicationMessageTemplate;

    public SmsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendTempIdSms(String mobile, String tempId) {
        if (!smsEnabled) {
            log.info("[SMS SKIPPED] To: {}, TEMP_ID: {}", mobile, tempId);
            return;
        }
        if (mobile == null || mobile.isBlank()) {
            log.warn("[SMS] No mobile number for TEMP_ID={}", tempId);
            return;
        }

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = LocalDateTime.now().format(fmt);

            // Build one SMS item (matches your working Postman body)
            SmsDataProjectCosting sms = new SmsDataProjectCosting();
            sms.setAcct_number("TEMP-ID");            // optional
            sms.setAlias(alias);
            sms.setApp_ref_key(appKey);               // <-- IMPORTANT: set app_ref_key
            sms.setAppkey(appKey);                    // <-- and appkey
            sms.setCost_cnter("001001");              // or derive from deptId if you want
            sms.setExpr_date(now);
            sms.setMessage(String.format(messageTemplate, tempId));
            sms.setPr_key(1);
            sms.setProc_date(now);
            sms.setProc_flag(procFlag);               // "W"
            sms.setRecno(0);
            sms.setRef_id(tempId);                    // TEMP_ID here
            sms.setShdl_date(now);
            sms.setTele_no(mobile);                   // recipient

            // Wrap it exactly as the gateway expects: { "smsDetailsList": [...] }
            SmsDetailsRequest payload = new SmsDetailsRequest(Collections.singletonList(sms));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<SmsDetailsRequest> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<String> resp = restTemplate.postForEntity(smsApiUrl, entity, String.class);
            log.info("[SMS] POST {} -> status={} body={}", smsApiUrl, resp.getStatusCode(), resp.getBody());

        } catch (Exception ex) {
            // Do NOT fail the main flow if SMS fails—just log it.
            log.error("Failed to send TEMP_ID SMS. mobile={}, tempId={}, error={}", mobile, tempId, ex.getMessage(), ex);
        }
    }

    @Override
    public void sendApplicationNoSms(String mobile, String applicationNo, String deptId) {
        if (!smsEnabled) {
            log.info("[SMS SKIPPED] To: {}, APP_NO: {}", mobile, applicationNo);
            return;
        }
        if (mobile == null || mobile.isBlank()) {
            log.warn("[SMS] No mobile number for APP_NO={}", applicationNo);
            return;
        }

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = LocalDateTime.now().format(fmt);

            SmsDataProjectCosting sms = new SmsDataProjectCosting();
            sms.setAcct_number("APP-NO");              // optional
            sms.setAlias(alias);
            sms.setApp_ref_key(appKey);
            sms.setAppkey(appKey);
            // If you want the real cost center, pass deptId from caller:
            sms.setCost_cnter(deptId != null ? deptId : "001001");
            sms.setExpr_date(now);
            sms.setMessage(String.format(applicationMessageTemplate, applicationNo));
            sms.setPr_key(1);
            sms.setProc_date(now);
            sms.setProc_flag(procFlag);
            sms.setRecno(0);
            sms.setRef_id(applicationNo);              // << APPLICATION_NO here
            sms.setShdl_date(now);
            sms.setTele_no(mobile);

            SmsDetailsRequest payload = new SmsDetailsRequest(Collections.singletonList(sms));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<SmsDetailsRequest> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<String> resp = restTemplate.postForEntity(smsApiUrl, entity, String.class);
            log.info("[SMS] POST {} -> status={} body={}", smsApiUrl, resp.getStatusCode(), resp.getBody());

        } catch (Exception ex) {
            log.error("Failed to send ApplicationNo SMS. mobile={}, appNo={}, error={}",
                    mobile, applicationNo, ex.getMessage(), ex);
        }
    }

    // request wrapper DTO: { "smsDetailsList": [ ... ] }
    public static class SmsDetailsRequest {
        private List<SmsDataProjectCosting> smsDetailsList;
        public SmsDetailsRequest() {}
        public SmsDetailsRequest(List<SmsDataProjectCosting> smsDetailsList) { this.smsDetailsList = smsDetailsList; }
        public List<SmsDataProjectCosting> getSmsDetailsList() { return smsDetailsList; }
        public void setSmsDetailsList(List<SmsDataProjectCosting> smsDetailsList) { this.smsDetailsList = smsDetailsList; }
    }
}
