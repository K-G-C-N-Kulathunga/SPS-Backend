package com.it.sps.controller;

import com.it.sps.dto.PivGenerateDto;
import com.it.sps.service.PivGenerateService;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/piv")
public class PivGenerateController {

    private final PivGenerateService pivGenerateService;

    public PivGenerateController(PivGenerateService pivGenerateService) {
        this.pivGenerateService = pivGenerateService;
    }

    @GetMapping("/customer-details")
    public ResponseEntity<?> getCustomerDetails(@RequestParam("applicationNo") String applicationNo) {
        if (!hasText(applicationNo)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Application number is required"));
        }

        Optional<PivGenerateDto> customerDetails = pivGenerateService.getCustomerDetails(applicationNo);
        return customerDetails.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Customer details not found for application number " + applicationNo)));
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
