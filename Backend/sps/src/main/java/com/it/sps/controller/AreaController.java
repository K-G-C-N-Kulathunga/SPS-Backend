package com.it.sps.controller;

import com.it.sps.dto.AreaDto;
import com.it.sps.dto.DepotDto;
import com.it.sps.entity.Gldeptin;
import com.it.sps.repository.GldeptinRepository;
import com.it.sps.service.AreaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cscNo")
public class AreaController {

    private final AreaService areaService;
    private final GldeptinRepository glDeptInRepository;

    // ✅ Inject both dependencies in constructor
    public AreaController(AreaService areaService, GldeptinRepository glDeptInRepository) {
        this.areaService = areaService;
        this.glDeptInRepository = glDeptInRepository;
    }

    @GetMapping("/areas")
    public List<AreaDto> getAreaDepartmentsAsDto() {
        return areaService.getAllAreaDepartmentsAsDto();
    }

    @GetMapping("/depots")
    public List<DepotDto> getDepotDepartmentsByAreaCode(@RequestParam String deptId) {
        return areaService.getDepotDepartmentsByAreaCode(deptId);
    }

    @GetMapping("/cscs")
    public List<Gldeptin> getCscsByArea(@RequestParam String area) {
        return glDeptInRepository.findByDeptAreaIgnoreCase(area);
    }
}
