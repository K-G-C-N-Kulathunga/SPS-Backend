package com.it.sps.service;

import com.it.sps.dto.SpestcndDTO;
import com.it.sps.entity.Spestcnd;
import com.it.sps.entity.SpestcndId;
import com.it.sps.repository.SpestcndRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SpestcndService {

    @Autowired
    private SpestcndRepository spestcndRepository;

    public Spestcnd saveBasicInfo(SpestcndDTO dto) {
        Spestcnd spestcnd = new Spestcnd();

        SpestcndId id = new SpestcndId();
        id.setDeptId(dto.getDeptId());
        id.setContractorId(dto.getContractorId());

        // Generate project number based on current timestamp
        String projectNo = "PRJ-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        id.setProjectNo(projectNo);

        spestcnd.setId(id);
        spestcnd.setConsumerName(dto.getName());

        return spestcndRepository.save(spestcnd);
    }
}
