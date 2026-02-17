package com.it.sps.service;

import com.it.sps.dto.CostItemDTO;
import java.util.List;

public interface CostService {
    List<CostItemDTO> getCostItemsByApplicationNo(String applicationNo);
}
