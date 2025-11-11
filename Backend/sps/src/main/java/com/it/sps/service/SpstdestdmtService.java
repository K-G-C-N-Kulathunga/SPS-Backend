package com.it.sps.service;

import com.it.sps.dto.SpstdestdmtDto;
import com.it.sps.entity.Spstdestdmt;
import com.it.sps.repository.SpstdestdmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpstdestdmtService {

    @Autowired
    private SpstdestdmtRepository spstdestdmtRepository;

    public List<SpstdestdmtDto> findAllByStdNo(String stdNo) {
        List<Spstdestdmt> entities = spstdestdmtRepository.findAllByIdStdNo(stdNo);
        return entities.stream().map(entity -> {
            SpstdestdmtDto dto = new SpstdestdmtDto();
            dto.setStdNo(entity.getId().getStdNo());
            dto.setLineType(entity.getId().getLineType());
            dto.setLength(entity.getLength());
            dto.setLineCost(entity.getLineCost());
            dto.setEstCost(entity.getEstCost());
            dto.setDeptId(entity.getDeptId());
            dto.setUom(entity.getUom());
            dto.setLinedes(entity.getLinedes());
            return dto;
        }).collect(Collectors.toList());
    }
}