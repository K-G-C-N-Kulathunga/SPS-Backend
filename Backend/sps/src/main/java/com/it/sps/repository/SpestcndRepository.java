package com.it.sps.repository;

import com.it.sps.entity.Spestcnd;
import com.it.sps.entity.SpestcndId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpestcndRepository extends JpaRepository<Spestcnd, SpestcndId> {
}
