package com.it.sps.controller;

import com.it.sps.dto.SpSetWirDto;
import com.it.sps.entity.SpSetWir;
import com.it.sps.service.SpSetWirService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sps/api/spsetwir")
public class SpSetWirController {

    private final SpSetWirService spSetWirService;

    public SpSetWirController(SpSetWirService spSetWirService) {
        this.spSetWirService = spSetWirService;
    }

    @PostMapping("/save")
    public ResponseEntity<List<SpSetWir>> save(@RequestBody SpSetWirDto dto) {
        List<SpSetWir> saved = spSetWirService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{applicationNo}/{deptId}")
    public ResponseEntity<List<SpSetWir>> getByApplicationAndDept(@PathVariable String applicationNo,
                                                                 @PathVariable String deptId) {
        List<SpSetWir> wires = spSetWirService.findByApplicationAndDept(applicationNo, deptId);
        return ResponseEntity.ok(wires);
    }

    @DeleteMapping("/{applicationNo}/{deptId}")
    public ResponseEntity<Void> deleteByApplicationAndDept(@PathVariable String applicationNo,
                                                          @PathVariable String deptId) {
        spSetWirService.deleteByApplicationAndDept(applicationNo, deptId);
        return ResponseEntity.noContent().build();
    }
}

