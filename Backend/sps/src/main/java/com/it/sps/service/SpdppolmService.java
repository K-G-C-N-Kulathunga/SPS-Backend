package com.it.sps.service;

import com.it.sps.dto.SpdppolmDTO;
import com.it.sps.entity.Spdppolm;
import com.it.sps.repository.SpdppolmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpdppolmService {

    private final SpdppolmRepository repository;

    public SpdppolmService(SpdppolmRepository repository) {
        this.repository = repository;
    }

    // Fetch MAT_CD by departmentId
    public List<SpdppolmDTO> getMatCdByDeptId(String deptId) {
        // "Y" means active
        List<Spdppolm> list = repository.findByIdDeptIdAndIsActive(deptId, "Y");

        return list.stream()
                .map(sp -> new SpdppolmDTO(
                        sp.getId().getDeptId(),
                        sp.getId().getMatCd(),
                        sp.getIsActive()
                ))
                .collect(Collectors.toList());
    }
}
