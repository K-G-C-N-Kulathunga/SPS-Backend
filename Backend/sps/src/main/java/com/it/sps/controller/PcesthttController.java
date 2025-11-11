package com.it.sps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.it.sps.dto.PcesthttDTO;
import com.it.sps.entity.Pcesthtt;
import com.it.sps.entity.PcesthttPK;
import com.it.sps.service.PcesthttService;

@RestController
@RequestMapping("/api")
public class PcesthttController {
	
	@Autowired
	private PcesthttService service;
	
	@PostMapping("/pcesthtt")
	public Pcesthtt createEntity(@RequestBody PcesthttDTO pcesthttDTO) {
		return service.saveEntity(pcesthttDTO);
	}
	
	@GetMapping("//pcesthtt/{estimateNo}/{deptId}/{revNo}")
	public Pcesthtt getEntity(String estimateNo, String deptId, int revNo) {
		PcesthttPK id = new PcesthttPK();
		id.setEstimateNo(estimateNo);
		id.setDeptId(deptId);
		id.setRevNo(revNo);
		
		
		
		return service.getEntity(id);	
	}

}
