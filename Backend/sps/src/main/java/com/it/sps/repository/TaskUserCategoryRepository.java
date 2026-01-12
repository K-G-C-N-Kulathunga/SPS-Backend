package com.it.sps.repository;

import com.it.sps.entity.TaskUserCategory;
import com.it.sps.entity.TaskUserCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskUserCategoryRepository extends JpaRepository<TaskUserCategory, TaskUserCategoryId> {

    List<TaskUserCategory> findByUserRoleCode(String userRoleCode);

    List<TaskUserCategory> findByUserRoleCodeAndMenuCode(String userRoleCode, String menuCode);

    void deleteByUserRoleCodeAndMenuCodeAndActivityCode(String userRoleCode, String menuCode, String activityCode);

    void deleteByUserRoleCodeAndMenuCodeIn(String userRoleCode, List<String> menuCodes); // ✅ correct place
}
