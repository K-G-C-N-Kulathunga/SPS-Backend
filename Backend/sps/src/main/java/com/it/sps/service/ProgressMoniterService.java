package com.it.sps.service;

import com.it.sps.dto.ProgressMoniterDTO;
import com.it.sps.entity.ProgressMoniter;
import com.it.sps.repository.ProgressMoniterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressMoniterService {

    @Autowired
    private ProgressMoniterRepository repository;

    public ProgressMoniter saveProgressMoniter(ProgressMoniterDTO dto) {
        ProgressMoniter progressMoniter = new ProgressMoniter();
        progressMoniter.setId(dto.getId());
        progressMoniter.setDeptId(dto.getDeptId());
        progressMoniter.setName(dto.getName());
        progressMoniter.setPercentage(dto.getPercentage());
        return repository.save(progressMoniter);
    }
}