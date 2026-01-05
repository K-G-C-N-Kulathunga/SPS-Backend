package com.it.sps.service.impl;

import com.it.sps.dto.TaskUserCategoryDTO;
import com.it.sps.entity.TaskUserCategory;
import com.it.sps.entity.TaskUserCategoryId;
import com.it.sps.repository.TaskUserCategoryRepository;
import com.it.sps.service.TaskUserCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskUserCategoryServiceImpl implements TaskUserCategoryService {

    private final TaskUserCategoryRepository repo;

    public TaskUserCategoryServiceImpl(TaskUserCategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TaskUserCategoryDTO> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<TaskUserCategoryDTO> getByUserRoleCode(String userRoleCode) {
        return repo.findByUserRoleCode(userRoleCode).stream().map(this::toDto).toList();
    }

    @Override
    public List<TaskUserCategoryDTO> getByUserRoleAndMenu(String userRoleCode, String menuCode) {
        return repo.findByUserRoleCodeAndMenuCode(userRoleCode, menuCode).stream().map(this::toDto).toList();
    }

    @Override
    @Transactional
    public TaskUserCategoryDTO create(TaskUserCategoryDTO dto) {
        if (dto.getUserRoleCode() == null || dto.getUserRoleCode().isBlank())
            throw new IllegalArgumentException("userRoleCode is required");
        if (dto.getMenuCode() == null || dto.getMenuCode().isBlank())
            throw new IllegalArgumentException("menuCode is required");
        if (dto.getActivityCode() == null || dto.getActivityCode().isBlank())
            throw new IllegalArgumentException("activityCode is required");

        TaskUserCategoryId id = new TaskUserCategoryId(dto.getUserRoleCode(), dto.getMenuCode(), dto.getActivityCode());
        if (repo.existsById(id))
            throw new IllegalArgumentException("Mapping already exists");

        TaskUserCategory e = new TaskUserCategory();
        e.setUserRoleCode(dto.getUserRoleCode());
        e.setMenuCode(dto.getMenuCode());
        e.setActivityCode(dto.getActivityCode());

        return toDto(repo.save(e));
    }

    @Override
    @Transactional
    public void delete(String userRoleCode, String menuCode, String activityCode) {
        TaskUserCategoryId id = new TaskUserCategoryId(userRoleCode, menuCode, activityCode);
        if (!repo.existsById(id)) throw new NoSuchElementException("Mapping not found");
        repo.deleteById(id);
    }

    private TaskUserCategoryDTO toDto(TaskUserCategory e) {
        return new TaskUserCategoryDTO(e.getUserRoleCode(), e.getMenuCode(), e.getActivityCode());
    }
}
