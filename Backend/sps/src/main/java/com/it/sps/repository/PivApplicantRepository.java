package com.it.sps.repository;

import com.it.sps.dto.ApplicantDTO;
import com.it.sps.entity.PivApplicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PivApplicantRepository extends JpaRepository<PivApplicant, String> {

    @Modifying
    @Query(value = """
        INSERT INTO PIV_APPLICANT
        (PIV_NO, DEPT_ID, ID_NO, NAME, ADDRESS, TELEPHONE_NO, EMAIL)
        VALUES
        (:pivNo, :deptId, :idNo, :name, :address, :telephoneNo, :email)
        """, nativeQuery = true)
    void saveApplicant(
            @Param("pivNo")       String pivNo,
            @Param("deptId")      String deptId,
            @Param("idNo")        String idNo,
            @Param("name")        String name,
            @Param("address")     String address,
            @Param("telephoneNo") String telephoneNo,
            @Param("email")       String email
    );
}