package com.it.sps.service;

import com.it.sps.dto.SpstrutmDTO;
import com.it.sps.entity.Spstrutm;
import com.it.sps.repository.SpstrutmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpstrutmService {

    private final SpstrutmRepository repository;

    public SpstrutmService(SpstrutmRepository repository) {
        this.repository = repository;
    }

    // Fetch MAT_CD by departmentId
    public List<SpstrutmDTO> getMatCdByDeptId(String deptId) {
        List<Spstrutm> list = repository.findByIdDeptId(deptId);

        return list.stream()
                .map(sp -> new SpstrutmDTO(
                        sp.getId().getDeptId(),
                        sp.getId().getMatCd(),
                        sp.getMatQty()
                ))
                .collect(Collectors.toList());
    }
}
