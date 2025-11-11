package com.it.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.it.sps.entity.SpSetWir;
import com.it.sps.entity.SpSetWirPK;

import java.util.List;

public interface SpSetWirRepository extends JpaRepository<SpSetWir, SpSetWirPK> {
    // Find all wire details for a specific application and department
    List<SpSetWir> findByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);
    
    // Delete all wire details for a specific application and department
    void deleteByIdApplicationNoAndIdDeptId(String applicationNo, String deptId);
}

