package com.it.sps.service.impl;

import com.it.sps.dto.DeptTypeCreateRequest;
import com.it.sps.dto.DeptTypeDto;
import com.it.sps.dto.DeptTypeUpdateRequest;
import com.it.sps.entity.DeptType;
import com.it.sps.repository.DeptTypeMenuRepository;
import com.it.sps.repository.DeptTypeRepository;
import com.it.sps.service.DeptTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;


import java.util.List;

@Service
@Transactional
public class DeptTypeServiceImpl implements DeptTypeService {

    private final DeptTypeRepository deptRepo;
    private final DeptTypeMenuRepository deptTypeMenuRepo;

    public DeptTypeServiceImpl(DeptTypeRepository deptRepo, DeptTypeMenuRepository deptTypeMenuRepo) {
        this.deptRepo = deptRepo;
        this.deptTypeMenuRepo = deptTypeMenuRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeptTypeDto> getAll() {
        return deptRepo.findAll().stream()
                .map(d -> new DeptTypeDto(d.getDeptTypeCode(), d.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DeptTypeDto getOne(String deptTypeCode) {
        DeptType d = deptRepo.findById(deptTypeCode)
                .orElseThrow(() -> new RuntimeException("DeptType not found: " + deptTypeCode));
        return new DeptTypeDto(d.getDeptTypeCode(), d.getName());
    }

    @Override
    public DeptTypeDto create(DeptTypeCreateRequest req) {
        if (req.getDeptTypeCode() == null || req.getDeptTypeCode().trim().isEmpty())
            throw new RuntimeException("deptTypeCode is required");
        if (req.getName() == null || req.getName().trim().isEmpty())
            throw new RuntimeException("name is required");

        String code = req.getDeptTypeCode().trim();
        if (deptRepo.existsById(code))
            throw new RuntimeException("DeptType already exists: " + code);

        DeptType d = new DeptType();
        d.setDeptTypeCode(code);
        d.setName(req.getName().trim());

        DeptType saved = deptRepo.save(d);
        return new DeptTypeDto(saved.getDeptTypeCode(), saved.getName());
    }

    @Override
    public DeptTypeDto update(String deptTypeCode, DeptTypeUpdateRequest req) {
        DeptType d = deptRepo.findById(deptTypeCode)
                .orElseThrow(() -> new RuntimeException("DeptType not found: " + deptTypeCode));

        if (req.getName() == null || req.getName().trim().isEmpty())
            throw new RuntimeException("name is required");

        d.setName(req.getName().trim());
        DeptType saved = deptRepo.save(d);
        return new DeptTypeDto(saved.getDeptTypeCode(), saved.getName());
    }

    @Override
    public void delete(String deptTypeCode) {
        if (!deptRepo.existsById(deptTypeCode))
            throw new RuntimeException("DeptType not found: " + deptTypeCode);

        // Optional but recommended: delete mappings first to avoid FK constraint errors
        deptTypeMenuRepo.deleteById_DeptTypeCode(deptTypeCode);

        deptRepo.deleteById(deptTypeCode);
    }
}
