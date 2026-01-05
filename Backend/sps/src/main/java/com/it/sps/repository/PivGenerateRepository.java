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

    @Query("SELECT a FROM Application a JOIN FETCH a.applicant WHERE a.applicationNo = :applicationNo")
    Optional<Application> findByApplicationNoWithApplicant(@Param("applicationNo") String applicationNo);

        @Query(value = "SELECT c.AC_CD AS codeNo, TRIM(c.AC_NM) AS description "
            + "FROM GLTITLM a JOIN GLACGRPM b ON TRIM(a.TITLE_CD) = TRIM(b.TITLE_CD) "
            + "JOIN GLACCTM c ON TRIM(b.AC_CD) = TRIM(c.AC_CD) "
            + "WHERE TRIM(a.TITLE_CD) = :titleCd ORDER BY b.SORT_KEY",
            nativeQuery = true)
    List<PivAccountProjection> findChargeAccounts(@Param("titleCd") String titleCd);
}
