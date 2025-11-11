package com.it.sps.controller;
import org.springframework.web.bind.annotation.*;

import com.it.sps.service.UserroleActService;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private UserroleActService activityService;

    @GetMapping("/by-role/{roleName}")
    public List<String> getActivitiesByRole(@PathVariable String roleName) {
        return activityService.getActivityDescriptionsByRoleName(roleName);
    }
}
