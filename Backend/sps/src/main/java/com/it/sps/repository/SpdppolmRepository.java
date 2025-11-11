package com.it.sps.repository;

import com.it.sps.entity.Spdppolm;
import com.it.sps.entity.SpdppolmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpdppolmRepository extends JpaRepository<Spdppolm, SpdppolmId> {

    // Find all records by deptId and active flag
    List<Spdppolm> findByIdDeptIdAndIsActive(String deptId, String isActive);
}
