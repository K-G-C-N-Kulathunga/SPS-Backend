package com.it.sps.repository;

import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PivGenerateRepository extends JpaRepository<Application, ApplicationPK> {

    @Query("SELECT a FROM Application a JOIN FETCH a.applicant WHERE a.applicationNo = :applicationNo")
    Optional<Application> findByApplicationNoWithApplicant(@Param("applicationNo") String applicationNo);
}
