package com.it.sps.controller;

import com.it.sps.dto.DeptTypeCreateRequest;
import com.it.sps.dto.DeptTypeDto;
import com.it.sps.dto.DeptTypeUpdateRequest;
import com.it.sps.entity.DeptType;
import com.it.sps.repository.DeptTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept-types")
public class DeptTypeController {

    private final DeptTypeRepository repo;

    public DeptTypeController(DeptTypeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<DeptTypeDto> list() {
        return repo.findAll().stream()
                .map(d -> new DeptTypeDto(d.getDeptTypeCode(), d.getName()))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeptTypeDto create(@RequestBody DeptTypeCreateRequest req) {
        if (req.getDeptTypeCode() == null || req.getDeptTypeCode().trim().isEmpty())
            throw new RuntimeException("deptTypeCode required");
        if (req.getName() == null || req.getName().trim().isEmpty())
            throw new RuntimeException("name required");

        String code = req.getDeptTypeCode().trim();
        if (repo.existsById(code)) throw new RuntimeException("Department already exists: " + code);

        DeptType d = new DeptType();
        d.setDeptTypeCode(code);
        d.setName(req.getName().trim());

        DeptType saved = repo.save(d);
        return new DeptTypeDto(saved.getDeptTypeCode(), saved.getName());
    }

    @PutMapping("/{deptTypeCode}")
    public DeptTypeDto update(@PathVariable String deptTypeCode, @RequestBody DeptTypeUpdateRequest req) {
        DeptType d = repo.findById(deptTypeCode)
                .orElseThrow(() -> new RuntimeException("Department not found: " + deptTypeCode));

        if (req.getName() == null || req.getName().trim().isEmpty())
            throw new RuntimeException("name required");

        d.setName(req.getName().trim());
        DeptType saved = repo.save(d);

        return new DeptTypeDto(saved.getDeptTypeCode(), saved.getName());
    }

    @DeleteMapping("/{deptTypeCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String deptTypeCode) {
        if (!repo.existsById(deptTypeCode))
            throw new RuntimeException("Department not found: " + deptTypeCode);
        repo.deleteById(deptTypeCode);
    }
}
