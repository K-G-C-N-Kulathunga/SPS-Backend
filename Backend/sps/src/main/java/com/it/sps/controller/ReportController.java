package com.it.sps.controller;

import com.it.sps.service.ReportService;
import com.it.sps.service.ApplicationReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/report")
@CrossOrigin(originPatterns = "*") // Allow requests from frontend
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ApplicationReportService applicationReportService;

    // -------------------------------
    // Existing endpoint for applicants
    // -------------------------------
    @GetMapping("/appli")
    public void exportApplicantsReport(
            @RequestParam String applicationNo,
            HttpServletResponse response,
            HttpSession session) throws Exception {

        reportService.generateApplicantReport(applicationNo, session, response);
    }

    // -------------------------------
    // WildFly-safe PDF download endpoint
    // Uses query parameter to avoid encoded slashes in path
    // -------------------------------
    @GetMapping("/download-application")
    public void downloadApplicationPdf(
            @RequestParam("applicationNo") String applicationNo,
            HttpServletResponse response) throws java.io.IOException {

        try {
            applicationReportService.generateApplicationPdf(applicationNo, response);
        } catch (Exception e) {
            // 👇 PRINT THE REAL ERROR TO THE CONSOLE
            e.printStackTrace();

            // 👇 AND SEND IT TO THE BROWSER (So you can read it)
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/plain");
            response.getWriter().write("FAILED: " + e.getMessage());
        }
    }
}
