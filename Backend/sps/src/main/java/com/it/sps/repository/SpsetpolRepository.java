package com.it.sps.repository;

import com.it.sps.entity.Spsetpol;
import com.it.sps.entity.SpsetpolPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpsetpolRepository extends JpaRepository<Spsetpol, SpsetpolPK> {
    List<Spsetpol> findByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);

    void deleteByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);
}
