package com.it.sps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.sps.dto.PcesthttDTO;
import com.it.sps.entity.Pcesthtt;
import com.it.sps.entity.PcesthttPK;
import com.it.sps.repository.PcesthttRepository;

@Service
public class PcesthttServiceImpl implements PcesthttService {
	
	@Autowired
	private PcesthttRepository repository;
	
	@Override
	public Pcesthtt saveEntity(PcesthttDTO pcesthttDTO) {
		Pcesthtt pcesthtt = new Pcesthtt();
		PcesthttPK id = new PcesthttPK();
		id.setEstimateNo(pcesthttDTO.getEstimateNo());
		id.setEstimateNo(pcesthttDTO.getEstimateNo());
		id.setDeptId(pcesthttDTO.getDeptId());
		pcesthtt.setId(id);
		pcesthtt.setEtimateDt(pcesthttDTO.getEtimateDt());
		pcesthtt.setEntDt(pcesthttDTO.getEntDt());
		pcesthtt.setConfDt(pcesthttDTO.getConfDt());
		pcesthtt.setAprDt1(pcesthttDTO.getAprDt1());
		pcesthtt.setAprDt2(pcesthttDTO.getAprDt2());
		pcesthtt.setAprDt3(pcesthttDTO.getAprDt3());
		pcesthtt.setAprDt4(pcesthttDTO.getAprDt4());
		pcesthtt.setAprDt5(pcesthttDTO.getAprDt5());
		pcesthtt.setRejctDt(pcesthttDTO.getRejctDt());
		pcesthtt.setReviseDt(pcesthttDTO.getReviseDt());
		return repository.save(pcesthtt);
	}
	
	@Override
	public List<Pcesthtt> getAllEntities() {
		return repository.findAll();
	}
	
	@Override
	public Pcesthtt getEntity(PcesthttPK id) {
		return repository.findById(id).orElse(null);
	}
	
	

}
