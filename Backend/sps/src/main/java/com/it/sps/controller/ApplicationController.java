package com.it.sps.controller;

import com.it.sps.dto.ApplicationDTO;
import com.it.sps.dto.FormDataDto;
import com.it.sps.entity.Application;
import com.it.sps.service.ApplicationService;
import com.it.sps.service.ApplicationWiringLDService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

	@Autowired
    private ApplicationWiringLDService applicationWiringLDService;
	@Autowired
    private ApplicationService applicationService;
    
    //private ApplicationWiringLDService applicationService;

    /*public ApplicationController(ApplicationWiringLDService applicationService) {
        this.applicationService = applicationService;
    }*/

    @PostMapping
    public ResponseEntity<?> submitApplication(@RequestBody FormDataDto formData) {
        try {
            if (!formData.getApplicationDto().getDeptId().matches("^\\d{3}\\.\\d{2}$")) {
                throw new IllegalArgumentException("Invalid Cost center number format");
            }

            // Save the application and get the generated applicationNo
            String applicationNo = applicationWiringLDService.saveFullApplication(formData);

            // Return the applicationNo in the response
            return ResponseEntity.ok().body(Map.of(
                    "applicationNo", applicationNo,
                    "message", "Application submitted successfully"
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    //get the genarating applicationId
    @GetMapping("/new-id")
    public ResponseEntity<String> getNewApplicationId(@RequestParam String deptId) {
        String newAppId = applicationWiringLDService.createNewApplicationId(deptId);
        return ResponseEntity.ok(newAppId);
    }
    
    @GetMapping("/all")
    public List<String> getAllApplicationNos() {
        return applicationService.getAllApplicationNos();
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateApplicationNo(@RequestParam String number) {
        boolean exists = applicationService.validateApplicationNo(number);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/save")
    public ResponseEntity<Application> saveApplication(@RequestBody ApplicationDTO applicationDTO, HttpSession session) {

        // Retrieve user details from the session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(401).build(); // Unauthorized if session expired
        }

        Application savedApplication = applicationService.saveApplication(applicationDTO, username);
        return ResponseEntity.ok(savedApplication);
    }



    @PatchMapping("/update")
    public ResponseEntity<?> updateApplication(
            @RequestParam String applicationId,
            @RequestBody ApplicationDTO applicationDTO,
            HttpSession session) {

        String sessionUsername = (String) session.getAttribute("username");
        if (sessionUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        try {
            Application updatedApplication = applicationService.updateApplication(applicationId, applicationDTO, sessionUsername);
            return ResponseEntity.ok(updatedApplication);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> getApplicationById(@RequestParam String applicationId) {
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        return application.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}