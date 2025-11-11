package com.it.sps.controller;

import com.it.sps.dto.SpsErestDto;
import com.it.sps.entity.SpsErest;
import com.it.sps.service.SpsErestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sps/api/spserest") // align with your other controllers
public class SpsErestController {

    private final SpsErestService spsErestService;

    public SpsErestController(SpsErestService spsErestService) {
        this.spsErestService = spsErestService;
    }

    @PostMapping("/save")
    public ResponseEntity<SpsErest> save(@RequestBody SpsErestDto dto) {
        SpsErest saved = spsErestService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{applicationNo}/{deptId}")
    public ResponseEntity<SpsErest> getOne(@PathVariable String applicationNo,
                                           @PathVariable String deptId) {
        return ResponseEntity.ok(spsErestService.findOne(applicationNo, deptId));
    }

    @DeleteMapping("/{applicationNo}/{deptId}")
    public ResponseEntity<Void> delete(@PathVariable String applicationNo,
                                       @PathVariable String deptId) {
        spsErestService.delete(applicationNo, deptId);
        return ResponseEntity.noContent().build();
    }
}
