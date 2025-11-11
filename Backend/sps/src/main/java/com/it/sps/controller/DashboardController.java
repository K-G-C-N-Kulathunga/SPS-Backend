package com.it.sps.controller;

import com.it.sps.entity.Dashboard;
import com.it.sps.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;


    //first filling of the customer details form
    @PostMapping
    public Dashboard createDashboard(@RequestBody Dashboard dashboard) {
        System.out.println("Received Dashboard payload: " + dashboard);
        Dashboard saved = dashboardService.saveDashboard(dashboard);
        System.out.println("Saved Dashboard: " + saved);
        return saved;
    }
}