package com.it.sps.repository;

import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;
import com.it.sps.repository.projection.PivAccountProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PivGenerateRepository extends JpaRepository<Application, ApplicationPK> {

    // Get customer + applicant details
    @Query("SELECT a FROM Application a JOIN FETCH a.applicant WHERE a.applicationNo = :applicationNo")
    Optional<Application> findByApplicationNoWithApplicant(
            @Param("applicationNo") String applicationNo);


    // Get PIV Type Name
    @Query(value = """
        SELECT TITLE_NM
        FROM GLTITLM
        WHERE TRIM(TITLE_CD) = :titleCd
        """, nativeQuery = true)
    String findPivTypeName(@Param("titleCd") String titleCd);


    // Get charge accounts
    @Query(value = """
        SELECT 
            TRIM(c.AC_CD) AS codeNo,
            TRIM(c.AC_NM) AS description,
            NVL(SUM(se.AMOUNT), 0) AS amount
        FROM GLTITLM a
        JOIN GLACGRPM b 
            ON TRIM(a.TITLE_CD) = TRIM(b.TITLE_CD)
        JOIN GLACCTM c 
            ON TRIM(b.AC_CD) = TRIM(c.AC_CD)
        LEFT JOIN PIV_COST_ITEM pci 
            ON TRIM(pci.AC_CD) = TRIM(c.AC_CD)
           AND TRIM(pci.PIV_TYPE) = :pivType
        LEFT JOIN STD_ESTIMATE se
            ON TRIM(se.COST_ITEM_CODE) = TRIM(pci.COST_ITEM_CODE)
           AND TRIM(se.ESTIMATE_NO) = :estimateNo
        WHERE TRIM(a.TITLE_CD) = :titleCd
        GROUP BY c.AC_CD, TRIM(c.AC_NM), b.SORT_KEY
        ORDER BY b.SORT_KEY
        """,
            nativeQuery = true)
    List<PivAccountProjection> findChargeAccounts(
            @Param("titleCd") String titleCd,
            @Param("pivType") String pivType,
            @Param("estimateNo") String estimateNo);
}