package com.it.sps.service;

import com.it.sps.dto.SpSetWirDto;
import com.it.sps.entity.SpSetWir;
import java.util.List;

public interface SpSetWirService {
    List<SpSetWir> save(SpSetWirDto dto);
    List<SpSetWir> findByApplicationAndDept(String applicationNo, String deptId);
    void deleteByApplicationAndDept(String applicationNo, String deptId);
}

