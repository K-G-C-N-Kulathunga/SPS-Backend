package com.it.sps.service;

import java.util.List;

import com.it.sps.dto.PcestdttDTO;
import com.it.sps.dto.SpestlabDTO;
import com.it.sps.entity.Pcestdtt;
import com.it.sps.entity.Spestlab;

public interface SpestlabService {
	
	void saveEntities(List<PcestdttDTO> pcestdttDTOs, List<SpestlabDTO> spestlabDTOs) throws Exception;
	List<Pcestdtt> getAllEntities();
	List<Spestlab> getAllLabourEntities();

}
