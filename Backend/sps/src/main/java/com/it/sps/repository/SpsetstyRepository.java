package com.it.sps.repository;

import com.it.sps.entity.Spsetsty;
import com.it.sps.entity.SpsetstyPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpsetstyRepository extends JpaRepository<Spsetsty, SpsetstyPK> {
    List<Spsetsty> findByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);

    void deleteByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);
}
