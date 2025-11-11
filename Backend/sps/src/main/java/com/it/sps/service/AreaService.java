package com.it.sps.service;

import com.it.sps.dto.AreaDto;
import com.it.sps.dto.DepotDto;
import com.it.sps.repository.GldeptinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    private final GldeptinRepository gldeptinRepository;

    public AreaService(GldeptinRepository gldeptinRepository) {
        this.gldeptinRepository = gldeptinRepository;
    }

    public List<AreaDto> getAllAreaDepartmentsAsDto() {
        return gldeptinRepository.findAreaDepartmentsAsDto();
    }

    public List<DepotDto> getDepotDepartmentsByAreaCode(String deptId) {
        return gldeptinRepository.findDepotDepartments(deptId);
    }

}
