package com.it.sps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.it.sps.dto.PcestdttDTO;
import com.it.sps.entity.Pcestdtt;
import com.it.sps.service.PcestdttService;

@RestController
@RequestMapping("/api")
public class PcestdttController {
	
	@Autowired
	public PcestdttService service;
	
	@PostMapping("/pcestdtt")
	public Pcestdtt createEntity(@RequestBody PcestdttDTO pcestdttDTO) {
		
		try {
			return service.saveEntity(pcestdttDTO);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save entity", e);
		}
		
	}
	
	@GetMapping("/pcestdtt")
	public List<Pcestdtt> getAllEntities(){
		return service.getAllEntities();
	}

}
