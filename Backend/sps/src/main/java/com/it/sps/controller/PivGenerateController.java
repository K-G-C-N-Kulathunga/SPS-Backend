package com.it.sps.controller;

import com.it.sps.dto.PivChargeResponseDto;
import com.it.sps.dto.PivGenerateDto;
import com.it.sps.dto.PivRequestDTO;
import com.it.sps.service.PivGenerateService;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/piv")
public class PivGenerateController {

    private final PivGenerateService pivGenerateService;

    public PivGenerateController(PivGenerateService pivGenerateService) {
        this.pivGenerateService = pivGenerateService;
    }

    // ================================
    // GET CUSTOMER DETAILS
    // ================================
    @GetMapping("/customer-details")
    public ResponseEntity<?> getCustomerDetails(
            @RequestParam(value = "applicationNo", required = false) String applicationNo,
            @RequestParam(value = "estimateNo", required = false) String estimateNo) {

        String lookupNo = Optional.ofNullable(applicationNo).orElse(estimateNo);

        if (!hasText(lookupNo)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Application number or estimate number is required"));
        }

        Optional<PivGenerateDto> customerDetails =
                pivGenerateService.getCustomerDetails(lookupNo);

        return customerDetails.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Customer details not found for " + lookupNo)));
    }


    // ================================
    // GET PIV TYPE NAME
    // ================================
    @GetMapping("/piv-type")
    public ResponseEntity<?> getPivType(@RequestParam String titleCd){

        String pivTypeName = pivGenerateService.getPivTypeName(titleCd);

        return ResponseEntity.ok(Map.of(
                "titleCd", titleCd,
                "pivTypeName", pivTypeName
        ));
    }


    // ================================
    // GET CHARGES
    // ================================
    @GetMapping("/charges")
    public ResponseEntity<PivChargeResponseDto> getChargeAccounts(
            @RequestParam(value = "titleCd", defaultValue = "PIV-SVC") String titleCd,
            @RequestParam(value = "pivType") String pivType,
            @RequestParam(value = "estimateNo") String estimateNo) {

        System.out.println("Fetching charges for estimateNo: " + estimateNo +
                ", pivType: " + pivType + ", titleCd: " + titleCd);

        PivChargeResponseDto response =
                pivGenerateService.getChargeAccounts(titleCd, pivType, estimateNo);

        return ResponseEntity.ok(response);
    }


    // ================================
    // SAVE PIV (NEW API)
    // ================================
    @PostMapping("/save")
    public ResponseEntity<?> savePiv(@RequestBody PivRequestDTO request) {

        try {

            String pivNo = pivGenerateService.savePiv(request);

            return ResponseEntity.ok(Map.of(
                    "message", "PIV saved successfully",
                    "pivNo", pivNo
            ));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    // ================================
    // HELPER METHOD
    // ================================
    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}