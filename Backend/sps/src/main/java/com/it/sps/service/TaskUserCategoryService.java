package com.it.sps.service;

import com.it.sps.dto.TaskUserCategoryDTO;

import java.util.List;

public interface TaskUserCategoryService {
    List<TaskUserCategoryDTO> getAll();
    List<TaskUserCategoryDTO> getByUserRoleCode(String userRoleCode);
    List<TaskUserCategoryDTO> getByUserRoleAndMenu(String userRoleCode, String menuCode);

    TaskUserCategoryDTO create(TaskUserCategoryDTO dto);
    void delete(String userRoleCode, String menuCode, String activityCode);
}
