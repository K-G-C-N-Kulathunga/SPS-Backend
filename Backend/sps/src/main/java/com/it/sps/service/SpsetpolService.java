package com.it.sps.service;

import com.it.sps.dto.SpsetpolDto;
import com.it.sps.entity.Spsetpol;

import java.util.List;

public interface SpsetpolService {
    List<Spsetpol> saveAll(String applicationNo, String deptId, List<SpsetpolDto> items);

    List<Spsetpol> findByApplicationAndDept(String applicationNo, String deptId);

    void deleteByApplicationAndDept(String applicationNo, String deptId);
}
