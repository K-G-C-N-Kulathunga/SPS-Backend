package com.it.sps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sps.dto.PcestdttDTO;
import com.it.sps.entity.Pcestdtt;
import com.it.sps.entity.PcestdttPK;
import com.it.sps.repository.PcestdttRepository;

@Service
public class PcestdttServiceImpl implements PcestdttService {
	
	@Autowired
	private PcestdttRepository repository;
	
	@Override
	public Pcestdtt saveEntity(PcestdttDTO pcestdttDTO) throws Exception {
		
		Pcestdtt pcestdtt = new Pcestdtt();
		PcestdttPK id = new PcestdttPK();
		id.setEstimateNo(pcestdttDTO.getEstimateNo());
		id.setRevNo(pcestdttDTO.getRevNo());
		id.setDeptId(pcestdttDTO.getDeptId());
		id.setResCd(pcestdttDTO.getResCd());
		
		pcestdtt.setId(id);
		pcestdtt.setEstimateQty(pcestdttDTO.getEstimateQty());
		pcestdtt.setEstimateCost(pcestdttDTO.getEstimateCost());
		
		try {
			return repository.save(pcestdtt);
		}catch(Exception e){
			throw new Exception("Error saving entity: " + e.getMessage(), e);
		}
		
	}
	
	@Override
	public List<Pcestdtt>getAllEntities(){
		return repository.findAll();
	}
	
	@Override
	public Pcestdtt getEntity(PcestdttPK id) throws Exception{
		return repository.findById(id).orElseThrow(() -> new Exception("Entity not found for ID: " + id));
	}

}
