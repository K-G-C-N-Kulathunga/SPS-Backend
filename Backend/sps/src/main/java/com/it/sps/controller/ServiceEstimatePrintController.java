package com.it.sps.controller;

import com.it.sps.service.ServiceEstimateReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications/connection-details")
public class ServiceEstimatePrintController {

    private final ServiceEstimateReportService reportService;

    public ServiceEstimatePrintController(ServiceEstimateReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Streams Service Estimate PDF without opening a new tab.
     * Usage: GET /api/applications/connection-details/service-estimate/print?applicationNo=...&deptId=...
     */
    @GetMapping(value = "/service-estimate/print", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> printServiceEstimate(
            @RequestParam String applicationNo,
            @RequestParam String deptId) {
        try {
            byte[] pdf = reportService.generatePdf(applicationNo, deptId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Inline so browser renders in iframe without prompting download
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=service-estimate-" + sanitize(applicationNo) + ".pdf");
            headers.add(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, max-age=0");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("X-Content-Type-Options", "nosniff");
            headers.add("Access-Control-Expose-Headers", HttpHeaders.CONTENT_DISPOSITION);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdf);
        } catch (Exception e) {
            // Return a concise diagnostic message to help locate the 500 root cause
            String message = "Failed to generate Service Estimate PDF: " + e.getMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String body = "{\"error\":\"" + message.replace("\"", "'") + "\",\"applicationNo\":\"" + sanitize(applicationNo) + "\",\"deptId\":\"" + sanitize(deptId) + "\"}";
            return ResponseEntity.status(500).headers(headers).body(body.getBytes());
        }
    }

    private static String sanitize(String s) {
        return s == null ? "" : s.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}
