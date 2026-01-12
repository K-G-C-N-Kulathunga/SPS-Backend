package com.it.sps.controller;

import com.it.sps.dto.DeptTypeMenuBulkUpdateRequest;
import com.it.sps.dto.DeptTypeMenuCreateRequest;
import com.it.sps.entity.DeptTypeMenu;
import com.it.sps.entity.DeptTypeMenuId;
import com.it.sps.repository.DeptTypeMenuRepository;
import com.it.sps.repository.DeptTypeRepository;
import com.it.sps.repository.MainMenuRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/dept-type-menus")
public class DeptTypeMenuController {

    private final DeptTypeMenuRepository repo;
    private final DeptTypeRepository deptRepo;
    private final MainMenuRepository menuRepo;

    public DeptTypeMenuController(
            DeptTypeMenuRepository repo,
            DeptTypeRepository deptRepo,
            MainMenuRepository menuRepo
    ) {
        this.repo = repo;
        this.deptRepo = deptRepo;
        this.menuRepo = menuRepo;
    }

    @GetMapping("/dept/{deptTypeCode}")
    public List<DeptTypeMenu> listByDept(@PathVariable String deptTypeCode) {
        return repo.findById_DeptTypeCode(deptTypeCode);
    }

    @GetMapping("/dept/{deptTypeCode}/menu-codes")
    public List<String> getDeptMenuCodes(@PathVariable String deptTypeCode) {
        return repo.findById_DeptTypeCode(deptTypeCode)
                .stream()
                .map(x -> x.getId().getMenuCode())
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeptTypeMenu create(@RequestBody DeptTypeMenuCreateRequest req) {

        if (!deptRepo.existsById(req.getDeptTypeCode())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Department not found: " + req.getDeptTypeCode());
        }

        if (!menuRepo.existsById(req.getMenuCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Menu not found: " + req.getMenuCode());
        }

        if (repo.existsById_DeptTypeCodeAndId_MenuCode(
                req.getDeptTypeCode(), req.getMenuCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Mapping already exists");
        }

        DeptTypeMenu m = new DeptTypeMenu();
        m.setId(new DeptTypeMenuId(req.getDeptTypeCode(), req.getMenuCode()));
        return repo.save(m);
    }

    @Transactional
    @PutMapping("/dept/{deptTypeCode}")
    public void replaceDeptMenus(
            @PathVariable String deptTypeCode,
            @RequestBody DeptTypeMenuBulkUpdateRequest req
    ) {

        if (!deptRepo.existsById(deptTypeCode)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Department not found: " + deptTypeCode);
        }

        Set<String> menuCodes = new LinkedHashSet<>(
                req.getMenuCodes() == null ? List.of() : req.getMenuCodes()
        );

        for (String code : menuCodes) {
            if (!menuRepo.existsById(code)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Menu not found: " + code);
            }
        }

        repo.deleteById_DeptTypeCode(deptTypeCode);

        for (String code : menuCodes) {
            DeptTypeMenu m = new DeptTypeMenu();
            m.setId(new DeptTypeMenuId(deptTypeCode, code));
            repo.save(m);
        }
    }

    @DeleteMapping("/dept/{deptTypeCode}/menu/{menuCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String deptTypeCode,
            @PathVariable String menuCode
    ) {
        repo.deleteById_DeptTypeCodeAndId_MenuCode(deptTypeCode, menuCode);
    }
}
