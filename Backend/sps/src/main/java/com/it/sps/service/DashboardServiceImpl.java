package com.it.sps.service;

import com.it.sps.entity.Dashboard;
import com.it.sps.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    public Dashboard saveDashboard(Dashboard dashboard) {
        System.out.println("Saving Dashboard in service: " + dashboard);
        Dashboard saved = dashboardRepository.save(dashboard);
        System.out.println("Dashboard saved in DB: " + saved);
        return saved;
    }
}