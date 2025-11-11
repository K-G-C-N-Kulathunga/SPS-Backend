package com.it.sps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.it.sps.dto.MaterialAndLabourDTO;
import com.it.sps.entity.Pcestdtt;
import com.it.sps.entity.Spestlab;
import com.it.sps.service.SpestlabService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
@RequestMapping("/api")
public class SpestlabController {
	
	@Autowired
	private SpestlabService service;
	
	@PostMapping("/spestlab")
	public void createEntities(@RequestBody MaterialAndLabourDTO materialAndLabourDTO) {
		
		try {
			service.saveEntities(materialAndLabourDTO.getPcestdttDTOs(), materialAndLabourDTO.getSpestlabDTOs());
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to save entities", e);
		}
	}
	
	@GetMapping("/Psestdtt")
	public List<Pcestdtt> getAllEntities(){
		return service.getAllEntities();
	}
	
	@GetMapping("/Spestlab")
	public List<Spestlab> getAllLabourEntities(){
		return service.getAllLabourEntities();
	}

}
