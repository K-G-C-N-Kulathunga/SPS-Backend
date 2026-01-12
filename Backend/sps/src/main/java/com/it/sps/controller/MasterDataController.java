package com.it.sps.controller;

import com.it.sps.dto.ApplicationSubTypeDropDownDTO;
import com.it.sps.dto.ApplicationLoanTypeDropDownDTO;
import com.it.sps.dto.ApplicationTariffCategoryDropDownDTO;
import com.it.sps.dto.ApplicationTariffCodeDropDownDTO;
import com.it.sps.repository.ApplicationSubTypeRepository;
import com.it.sps.repository.ApplicationLoanTypeRepository;
import com.it.sps.repository.TariffRepository;
import com.it.sps.repository.TariffCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/masterdata")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MasterDataController {

    private final ApplicationSubTypeRepository applicationSubTypeRepository;
    private final ApplicationLoanTypeRepository applicationLoanTypeRepository;
    private final TariffRepository tariffRepository;
    private final TariffCategoryRepository tariffCategoryRepository;

    public MasterDataController(ApplicationSubTypeRepository applicationSubTypeRepository,
                                ApplicationLoanTypeRepository applicationLoanTypeRepository,
                                TariffRepository tariffRepository,
                                TariffCategoryRepository tariffCategoryRepository) {
        this.applicationSubTypeRepository = applicationSubTypeRepository;
        this.applicationLoanTypeRepository = applicationLoanTypeRepository;
        this.tariffRepository = tariffRepository;
        this.tariffCategoryRepository = tariffCategoryRepository;
    }

    // ✅ DROPDOWN API ONLY
    @GetMapping("/applicationsubtype/{type}")
    public ResponseEntity<?> getSubTypesForDropdown(@PathVariable String type) {
        try {
            List<ApplicationSubTypeDropDownDTO> subTypes = applicationSubTypeRepository.findForDropdown(type);

            if (subTypes == null || subTypes.isEmpty()) {
                // Return 404 if nothing found
                return ResponseEntity.status(404)
                        .body("No subtypes found for type: " + type);
            }

            return ResponseEntity.ok(subTypes);

        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Return 500 with meaningful message
            return ResponseEntity.status(500)
                    .body("Internal Server Error while fetching subtypes: " + e.getMessage());
        }
    }

    // ✅ DROPDOWN API FOR LOAN TYPE (Status = 1)
    @GetMapping("/applicationloantype")
    public ResponseEntity<?> getLoanTypesForDropdown() {
        try {
            // 1. Call the new repository method that filters by status = 1
            List<ApplicationLoanTypeDropDownDTO> loanTypes = applicationLoanTypeRepository.findActiveLoanTypes();

            // 2. Check if list is empty
            if (loanTypes == null || loanTypes.isEmpty()) {
                return ResponseEntity.status(404)
                        .body("No active loan types found.");
            }

            // 3. Return the correct list variable
            return ResponseEntity.ok(loanTypes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Internal Server Error while fetching loantypes: " + e.getMessage());
        }
    }

    //Dropdown API for TariffCodes
    @GetMapping("/tariffcodes")
    public ResponseEntity<?> getTariffCodeDropDown() {
        try {
            List<ApplicationTariffCodeDropDownDTO> tariffCodes = tariffRepository.findActiveTariffCode();

            if (tariffCodes == null || tariffCodes.isEmpty()) {
                return ResponseEntity.status(404)
                        .body("No active Tariff code found.");
            }

            return ResponseEntity.ok(tariffCodes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Internal Server Error while fetching tariffCodes: " + e.getMessage());
        }
    }

    @GetMapping("/tariffCategory")
    public ResponseEntity<?> getTariffCategoryDropDown() {
        try{
            List<ApplicationTariffCategoryDropDownDTO> tariffCategory = tariffCategoryRepository.findActiveTariffCategory();

            if (tariffCategory == null || tariffCategory.isEmpty()) {
                return ResponseEntity.status(404)
                        .body("No active Tariff Category found.");
            }

            return ResponseEntity.ok(tariffCategory);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Internal Server Error while fetching Tariff Category: " + e.getMessage());
        }
    }
}
