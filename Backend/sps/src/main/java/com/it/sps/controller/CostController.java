package com.it.sps.controller;

import com.it.sps.dto.CostItemDTO;
import com.it.sps.service.CostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cost")
public class CostController {

    private final CostService costService;

    public CostController(CostService costService) {
        this.costService = costService;
    }

    @GetMapping("/by-application")
    public List<CostItemDTO> getCostByApplication(
            @RequestParam String applicationNo) {

        return costService.getCostItemsByApplicationNo(applicationNo);
    }
}
