package com.it.sps.repository;

import com.it.sps.entity.DeptTypeMenu;
import com.it.sps.entity.DeptTypeMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeptTypeMenuRepository extends JpaRepository<DeptTypeMenu, DeptTypeMenuId> {

    List<DeptTypeMenu> findById_DeptTypeCode(String deptTypeCode);

    boolean existsById_DeptTypeCodeAndId_MenuCode(String deptTypeCode, String menuCode);

    @Transactional
    void deleteById_DeptTypeCodeAndId_MenuCode(String deptTypeCode, String menuCode);

    @Transactional
    void deleteById_DeptTypeCode(String deptTypeCode);
}
