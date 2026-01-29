package com.it.sps.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.sps.entity.PivCostItem;
import com.it.sps.service.PivCostItemService;

@RestController
@RequestMapping("/piv-cost-item")
public class PivCostItemController {

    @Autowired
    private PivCostItemService service;

    @PostMapping
    public PivCostItem create(@RequestBody PivCostItem item) {
        return service.save(item);
    }
}
