package com.it.sps.repository;

import com.it.sps.entity.ProgressMoniter;
import com.it.sps.entity.ProgressMoniterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressMoniterRepository extends JpaRepository<ProgressMoniter, ProgressMoniterId> {
}