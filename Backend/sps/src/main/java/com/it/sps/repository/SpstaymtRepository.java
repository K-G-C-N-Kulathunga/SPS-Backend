package com.it.sps.repository;

import com.it.sps.entity.Spstaymt;
import com.it.sps.entity.SpstaymtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpstaymtRepository extends JpaRepository<Spstaymt, SpstaymtId> {

    List<Spstaymt> findByIdDeptId(String deptId);
}
