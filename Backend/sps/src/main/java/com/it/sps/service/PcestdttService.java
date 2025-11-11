package com.it.sps.service;

import java.util.List;

import com.it.sps.dto.PcestdttDTO;
import com.it.sps.entity.Pcestdtt;
import com.it.sps.entity.PcestdttPK;

public interface PcestdttService {
	
	Pcestdtt saveEntity(PcestdttDTO pcestdttDTO) throws Exception;
	List<Pcestdtt> getAllEntities();
	Pcestdtt getEntity(PcestdttPK id) throws Exception;

}
