package com.it.sps.repository;

import com.it.sps.entity.Spstdestdmt;
import com.it.sps.entity.SpstdestdmtId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpstdestdmtRepository extends JpaRepository<Spstdestdmt, SpstdestdmtId> {
    List<Spstdestdmt> findAllByIdStdNo(String stdNo);
}