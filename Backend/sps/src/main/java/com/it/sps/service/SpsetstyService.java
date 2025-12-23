package com.it.sps.service;

import com.it.sps.dto.SpsetstyDto;
import com.it.sps.entity.Spsetsty;

import java.util.List;

public interface SpsetstyService {
    List<Spsetsty> saveAll(String applicationNo, String deptId, List<SpsetstyDto> items);

    List<Spsetsty> findByApplicationAndDept(String applicationNo, String deptId);

    void deleteByApplicationAndDept(String applicationNo, String deptId);
}
