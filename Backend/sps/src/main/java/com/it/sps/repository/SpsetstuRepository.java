package com.it.sps.repository;

import com.it.sps.entity.Spsetstu;
import com.it.sps.entity.SpsetstuPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpsetstuRepository extends JpaRepository<Spsetstu, SpsetstuPK> {
    List<Spsetstu> findByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);

    void deleteByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);
}
