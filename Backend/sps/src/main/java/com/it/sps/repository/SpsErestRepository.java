package com.it.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpsErestPK;

import java.util.List;

public interface SpsErestRepository extends JpaRepository<SpsErest, SpsErestPK> {

	@Query("SELECT COUNT(s) > 0 FROM SpsErest s WHERE s.id.applicationNo = :applicationNo AND s.id.deptId = :deptId")
	boolean existsByApplicationNoAndDeptId(@Param("applicationNo") String applicationNo,
										   @Param("deptId") String deptId);

	@Query("SELECT DISTINCT s.id.applicationNo FROM SpsErest s WHERE s.id.deptId = :deptId")
	List<String> findUsedApplicationNosByDeptId(@Param("deptId") String deptId);
}
