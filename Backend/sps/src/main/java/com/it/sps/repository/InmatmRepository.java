package com.it.sps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.sps.entity.Inmatm;

public interface InmatmRepository extends JpaRepository<Inmatm, String> {

    List<Inmatm> findByMatNm(@RequestParam String name);

    // ✅ ADD THIS METHOD to fix the error
    // This query trims the database column 'MAT_CD' before comparing it with your input
    @Query("SELECT i FROM Inmatm i WHERE TRIM(i.matCd) = :matCd")
    Inmatm findByTrimmedMatCd(@Param("matCd") String matCd);

}