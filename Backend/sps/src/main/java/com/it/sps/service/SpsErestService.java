package com.it.sps.service;

import com.it.sps.dto.SpsErestDto;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpsErestPK;

public interface SpsErestService {
    SpsErest save(SpsErestDto dto);                    // create or update (upsert)
    SpsErest findOne(String applicationNo, String deptId);
    void delete(String applicationNo, String deptId);
}
