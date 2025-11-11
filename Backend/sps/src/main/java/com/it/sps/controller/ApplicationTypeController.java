package com.it.sps.controller;

import com.it.sps.dto.ApplicationTypeDto;
import com.it.sps.service.ApplicationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/application")
public class ApplicationTypeController {

    @Autowired
    private ApplicationTypeService service;

    @GetMapping("/type")
    public List<ApplicationTypeDto> getAllTypes() {
        return service.getAllApplicationTypes();
    }
}
