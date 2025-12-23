package com.it.sps.controller;

import com.it.sps.dto.ApplicantDTO;
import com.it.sps.entity.Applicant;
import com.it.sps.repository.ApplicantRepository;
import com.it.sps.service.ApplicantService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

	
	@Autowired
    private ApplicantService applicantService;

    /*public ApplicantController(ApplicantRepository applicantRepository, ApplicantService applicantService) {
        this.applicantRepository = applicantRepository;
        this.applicantService = applicantService;
    }*/

    @GetMapping("/{idNo}")
    public ResponseEntity<ApplicantDTO> getApplicantById(@PathVariable String idNo) {
        ApplicantDTO applicantDto = applicantService.findApplicantDTOByIdNo(idNo);

        if (applicantDto != null) {
            return new ResponseEntity<>(applicantDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public ResponseEntity<Applicant> addNewUser(@RequestBody ApplicantDTO applicantDto) {
        Applicant applicant = applicantService.createApplicant(applicantDto);
        return new ResponseEntity<>(applicant, HttpStatus.CREATED);
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchApplicantByIdNo(@RequestParam String idNo) {
        System.out.println("hello");

        Optional<ApplicantDTO> applicantDTO = applicantService.getApplicantById(idNo);

        if (applicantDTO.isPresent()) {
            return ResponseEntity.ok(applicantDTO.get());
        } else {
            return ResponseEntity.status(404)
                    .body("Applicant with ID " + idNo + " not found.");
        }
    }
    
 // Endpoint to update an applicant's data
    @PatchMapping("/{idNo}")
    public ResponseEntity<ApplicantDTO> updateApplicant(@PathVariable String idNo, @RequestBody ApplicantDTO updatedApplicantDTO) {
        try {
            ApplicantDTO updated = applicantService.updateApplicant(idNo, updatedApplicantDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if not found
        }
    }


    @PostMapping("/save")
    public ApplicantDTO createApplicant(@RequestBody ApplicantDTO applicantDTO) {
        return applicantService.saveApplicant(applicantDTO);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable String id) {
        if (applicantService.getApplicantById(id).isPresent()) {
            applicantService.deleteApplicant(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}