package com.it.sps.repository;

import com.it.sps.entity.Spstrutm;
import com.it.sps.entity.SpstrutmId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpstrutmRepository extends JpaRepository<Spstrutm, SpstrutmId> {

    // Find all records by deptId
    List<Spstrutm> findByIdDeptId(String deptId);
}
