package com.it.sps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sps.dto.MaterialDTO;
import com.it.sps.entity.Inmatm;
import com.it.sps.repository.InmatmRepository;
import com.it.sps.repository.SpestmtmRepository;

@Service
public class MaterialService {
	
	@Autowired
	private InmatmRepository inmatmRepository;
	
	@Autowired
    private SpestmtmRepository spestmtmRepository;


    public List<MaterialDTO> getMaterials(String deptId, long connectionType, String wiringType, long phase) {
        return spestmtmRepository.findMaterialsByCriteria(deptId, connectionType, wiringType, phase);

    }
	
	public MaterialService(InmatmRepository inmatmRepository) {
		this.inmatmRepository = inmatmRepository;
	}
	
	public List<Inmatm> getMaterialsByName(String name) {
        return inmatmRepository.findByMatNm(name);
        
    }
		

}
