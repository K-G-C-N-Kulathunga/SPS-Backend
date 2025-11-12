package com.it.sps.service;

import com.it.sps.dto.SpsetstuDto;
import com.it.sps.entity.Spsetstu;

import java.util.List;

public interface SpsetstuService {
    List<Spsetstu> saveAll(String applicationNo, String deptId, List<SpsetstuDto> items);

    List<Spsetstu> findByApplicationAndDept(String applicationNo, String deptId);

    void deleteByApplicationAndDept(String applicationNo, String deptId);
}
