package com.it.sps.service;

import java.util.List;

import com.it.sps.dto.PcesthttDTO;
import com.it.sps.entity.Pcesthtt;
import com.it.sps.entity.PcesthttPK;

public interface PcesthttService {
	Pcesthtt saveEntity(PcesthttDTO pcesthttDTO);
	List<Pcesthtt> getAllEntities();
	Pcesthtt getEntity(PcesthttPK Id);

}
