package com.it.sps.service.impl;

import com.it.sps.dto.DeptTypeMenuCreateRequest;
import com.it.sps.dto.DeptTypeMenuDto;
import com.it.sps.entity.DeptTypeMenu;
import com.it.sps.entity.DeptTypeMenuId;
import com.it.sps.entity.MainMenu;
import com.it.sps.repository.DeptTypeMenuRepository;
import com.it.sps.repository.DeptTypeRepository;
import com.it.sps.repository.MainMenuRepository;
import com.it.sps.service.DeptTypeMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeptTypeMenuServiceImpl implements DeptTypeMenuService {

    private final DeptTypeMenuRepository repo;
    private final DeptTypeRepository deptRepo;
    private final MainMenuRepository menuRepo;

    public DeptTypeMenuServiceImpl(
            DeptTypeMenuRepository repo,
            DeptTypeRepository deptRepo,
            MainMenuRepository menuRepo
    ) {
        this.repo = repo;
        this.deptRepo = deptRepo;
        this.menuRepo = menuRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeptTypeMenuDto> getByDeptType(String deptTypeCode) {
        return repo.findById_DeptTypeCode(deptTypeCode).stream().map(x -> {
            DeptTypeMenuId id = x.getId();
            String menuDisplayName = null;
            MainMenu mm = x.getMainMenu();
            if (mm != null) menuDisplayName = mm.getDisplayName();

            return new DeptTypeMenuDto(id.getDeptTypeCode(), id.getMenuCode(), menuDisplayName);
        }).toList();
    }

    @Override
    public DeptTypeMenuDto create(DeptTypeMenuCreateRequest req) {
        if (req.getDeptTypeCode() == null || req.getDeptTypeCode().trim().isEmpty())
            throw new RuntimeException("deptTypeCode is required");
        if (req.getMenuCode() == null || req.getMenuCode().trim().isEmpty())
            throw new RuntimeException("menuCode is required");

        String deptTypeCode = req.getDeptTypeCode().trim();
        String menuCode = req.getMenuCode().trim();

        if (!deptRepo.existsById(deptTypeCode))
            throw new RuntimeException("DeptType not found: " + deptTypeCode);

        if (!menuRepo.existsById(menuCode))
            throw new RuntimeException("MainMenu not found: " + menuCode);

        if (repo.existsById_DeptTypeCodeAndId_MenuCode(deptTypeCode, menuCode))
            throw new RuntimeException("Mapping already exists: " + deptTypeCode + " -> " + menuCode);

        DeptTypeMenu m = new DeptTypeMenu();
        m.setId(new DeptTypeMenuId(deptTypeCode, menuCode));
        DeptTypeMenu saved = repo.save(m);

        // Display name (optional)
        String displayName = menuRepo.findById(menuCode).map(MainMenu::getDisplayName).orElse(null);

        return new DeptTypeMenuDto(saved.getId().getDeptTypeCode(), saved.getId().getMenuCode(), displayName);
    }

    @Override
    public void delete(String deptTypeCode, String menuCode) {
        DeptTypeMenuId id = new DeptTypeMenuId(deptTypeCode, menuCode);
        if (!repo.existsById(id))
            throw new RuntimeException("Mapping not found: " + deptTypeCode + " -> " + menuCode);
        repo.deleteById(id);
    }
}
