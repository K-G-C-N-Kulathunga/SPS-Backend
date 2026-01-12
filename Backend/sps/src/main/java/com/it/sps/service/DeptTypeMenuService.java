package com.it.sps.service;

import com.it.sps.dto.DeptTypeMenuCreateRequest;
import com.it.sps.dto.DeptTypeMenuDto;

import java.util.List;

public interface DeptTypeMenuService {
    List<DeptTypeMenuDto> getByDeptType(String deptTypeCode);
    DeptTypeMenuDto create(DeptTypeMenuCreateRequest req);
    void delete(String deptTypeCode, String menuCode);
}
