package com.it.sps.service;

import com.it.sps.dto.DeptTypeCreateRequest;
import com.it.sps.dto.DeptTypeDto;
import com.it.sps.dto.DeptTypeUpdateRequest;

import java.util.List;

public interface DeptTypeService {
    List<DeptTypeDto> getAll();
    DeptTypeDto getOne(String deptTypeCode);
    DeptTypeDto create(DeptTypeCreateRequest req);
    DeptTypeDto update(String deptTypeCode, DeptTypeUpdateRequest req);
    void delete(String deptTypeCode);
}
