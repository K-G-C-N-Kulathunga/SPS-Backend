package com.it.sps.service.impl;

import com.it.sps.dto.CostItemDTO;
import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationSubTypeCost;
import com.it.sps.entity.CostItem;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.ApplicationSubTypeCostRepository;
import com.it.sps.service.CostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostServiceImpl implements CostService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationSubTypeCostRepository costRepository;

    public CostServiceImpl(ApplicationRepository applicationRepository,
                           ApplicationSubTypeCostRepository costRepository) {
        this.applicationRepository = applicationRepository;
        this.costRepository = costRepository;
    }

    @Override
    public List<CostItemDTO> getCostItemsByApplicationNo(String applicationNo) {

        // 1️⃣ Get only metadata from APPLICATIONS
        Application app = applicationRepository
                .findByApplicationNo(applicationNo)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // 2️⃣ Fetch cost configuration
        List<ApplicationSubTypeCost> mappings =
                costRepository.findByApplicationTypeAndApplicationSubTypeOrderByDisplayOrder(
                        app.getApplicationType(),
                        app.getApplicationSubType()
                );

        // 3️⃣ Map to DTO
        return mappings.stream().map(m -> {
            CostItem c = m.getCostItem();

            CostItemDTO dto = new CostItemDTO();
            dto.setCostItemCode(c.getCostItemCode());
            dto.setDescription(c.getDescription());
            dto.setDisplayOrder(m.getDisplayOrder());
            dto.setIsPercent(Boolean.TRUE.equals(c.getIsPercent()) ? 1 : 0);
            dto.setIsTotal(Boolean.TRUE.equals(c.getIsTotal()) ? 1 : 0);
            dto.setPercentCalcItem(c.getPercentCalcItem());
            dto.setTotalCalcItem(m.getTotalCalcItem());

            return dto;
        }).toList();
    }
}
