package com.it.sps.service;

import com.it.sps.dto.SpSetWirDto;
import com.it.sps.entity.Application;
import com.it.sps.entity.SpSetWir;
import com.it.sps.entity.SpSetWirPK;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.SpSetWirRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpSetWirServiceImpl implements SpSetWirService {

    private final ApplicationRepository applicationRepository;
    private final SpSetWirRepository spSetWirRepository;

    public SpSetWirServiceImpl(ApplicationRepository applicationRepository,
                               SpSetWirRepository spSetWirRepository) {
        this.applicationRepository = applicationRepository;
        this.spSetWirRepository = spSetWirRepository;
    }

    @Override
    public List<SpSetWir> save(SpSetWirDto dto) {
        // 1) Validate PK parts exist
        if (dto.getApplicationNo() == null || dto.getApplicationNo().isBlank()) {
            throw new IllegalArgumentException("applicationNo is required");
        }
        if (dto.getDeptId() == null || dto.getDeptId().isBlank()) {
            throw new IllegalArgumentException("deptId is required");
        }
        if (dto.getWireDetails() == null || dto.getWireDetails().isEmpty()) {
            throw new IllegalArgumentException("wireDetails cannot be empty");
        }

        // 2) Ensure Application exists
        System.out.println("DEBUG: SpSetWirService - Looking for application with applicationNo: " + dto.getApplicationNo());
        Application app = applicationRepository.findAllWithApplicant(dto.getApplicationNo());
        System.out.println("DEBUG: SpSetWirService - Found application: " + app);
        if (app == null) {
            throw new IllegalStateException("Application not found with applicationNo=" + dto.getApplicationNo());
        }

        // 3) Delete existing wire details for this application and department
        spSetWirRepository.deleteByIdApplicationNoAndIdDeptId(dto.getApplicationNo(), dto.getDeptId());

        // 4) Create new wire details
        List<SpSetWir> savedWires = new ArrayList<>();
        for (SpSetWirDto.WireDetail wireDetail : dto.getWireDetails()) {
            if (wireDetail.getMatCd() == null || wireDetail.getMatCd().isBlank()) {
                continue; // Skip invalid entries
            }

            SpSetWirPK pk = new SpSetWirPK(dto.getApplicationNo(), dto.getDeptId(), wireDetail.getMatCd());
            SpSetWir entity = new SpSetWir();
            entity.setId(pk);
            entity.setWireMode(wireDetail.getWireMode());
            entity.setWireType(wireDetail.getWireType());
            entity.setWireLength(wireDetail.getWireLength());

            SpSetWir saved = spSetWirRepository.save(entity);
            savedWires.add(saved);
        }

        return savedWires;
    }

    @Override
    public List<SpSetWir> findByApplicationAndDept(String applicationNo, String deptId) {
        return spSetWirRepository.findByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }

    @Override
    public void deleteByApplicationAndDept(String applicationNo, String deptId) {
        spSetWirRepository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }
}

