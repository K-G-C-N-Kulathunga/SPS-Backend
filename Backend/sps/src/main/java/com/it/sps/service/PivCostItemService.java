package com.it.sps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sps.entity.PivCostItem;
import com.it.sps.repository.PivCostItemRepository;

@Service
public class PivCostItemService {

    @Autowired
    private PivCostItemRepository repository;

    public PivCostItem save(PivCostItem item) {
        return repository.save(item);
    }
}
