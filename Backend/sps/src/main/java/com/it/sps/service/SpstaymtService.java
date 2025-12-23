package com.it.sps.service;

import com.it.sps.dto.SpstaymtDTO;
import com.it.sps.entity.Spstaymt;
import com.it.sps.repository.SpstaymtRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpstaymtService {

    private final SpstaymtRepository repository;

    public SpstaymtService(SpstaymtRepository repository) {
        this.repository = repository;
    }

    public List<SpstaymtDTO> getMatCdByDeptId(String deptId) {
        List<Spstaymt> list = repository.findByIdDeptId(deptId);

        return list.stream()
                .map(sp -> new SpstaymtDTO(
                        sp.getId().getDeptId(),
                        sp.getId().getMatCd(),
                        sp.getMatQty()
                ))
                .collect(Collectors.toList());
    }
}
