package com.it.sps.controller;

//import com.it.sps.service.ReportService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import jakarta.servlet.http.HttpServletResponse;
//
//@RestController
//@RequestMapping("/report")
//public class ReportController {
//
//    @Autowired
//    private ReportService reportService;
//
//    @GetMapping("/estimate/{estimateNo}")
//    public void generateEstimateReport(@PathVariable String estimateNo, HttpServletResponse response) throws Exception {
//        reportService.exportEstimateReport(estimateNo, response);
//    }
//}

import com.it.sps.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

//    @GetMapping("/applicants")
//    public void exportApplicantsReport(HttpServletResponse response) throws Exception {
//        reportService.generateApplicantReport(response);
//    }

    @GetMapping("/appli")
    public void exportApplicantsReport(
            @RequestParam String applicationNo,
            HttpServletResponse response,
            HttpSession session) throws Exception {
        reportService.generateApplicantReport(applicationNo,session, response);
    }
}