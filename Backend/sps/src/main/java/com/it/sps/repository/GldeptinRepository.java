package com.it.sps.repository;

import com.it.sps.dto.AreaDto;
import com.it.sps.dto.DepotDto;
import com.it.sps.entity.Gldeptin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GldeptinRepository extends JpaRepository<Gldeptin, String> {

    @Query("SELECT new com.it.sps.dto.AreaDto(d.deptId, d.deptArea) FROM Gldeptin d WHERE d.deptType = 'AREA' ORDER BY d.deptArea")
    List<AreaDto> findAreaDepartmentsAsDto();

    @Query("SELECT NEW com.it.sps.dto.DepotDto(d.deptId, d.deptFullName) " +
            "FROM Gldeptin d " +
            "WHERE d.deptType = 'DEPOT' " +
            "AND SUBSTRING(d.deptId, 1, 3) = SUBSTRING(:prefix,1,3) " +
            "ORDER BY d.deptFullName")
    List<DepotDto> findDepotDepartments(@Param("prefix") String deptId);
    List<Gldeptin> findByDeptAreaIgnoreCase(String deptArea);

    // ✅ ADDED THIS METHOD TO FIX THE ERROR
    // This maps the 'rptUser' argument (passed from LoginService) to the 'deptId' column
    @Query("SELECT d.deptId FROM Gldeptin d WHERE d.deptId = :rptUser")
    String findDeptIdByRptUser(@Param("rptUser") String rptUser);


}
