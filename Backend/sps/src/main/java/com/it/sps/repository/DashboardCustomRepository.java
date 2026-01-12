package com.it.sps.repository;

import com.it.sps.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// New repository just for custom queries
public interface DashboardCustomRepository extends JpaRepository<Dashboard, String> {

    Optional<Dashboard> findByApplicationIdAndDeptId(String applicationId, String deptId);

}
