package com.it.sps.controller;

import com.it.sps.dto.SpestcndDTO;
import com.it.sps.entity.Spestcnd;
import com.it.sps.service.SpestcndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/spestcnd")
public class SpestcndController {

    @Autowired
    private SpestcndService spestcndService;

    @PostMapping("/save")
    public ResponseEntity<Spestcnd> saveBasicInfo(@RequestBody SpestcndDTO dto) {
    	
    	System.out.println("spestcnd");
        Spestcnd saved = spestcndService.saveBasicInfo(dto);
        return ResponseEntity.ok(saved);
    }
    
    
}
