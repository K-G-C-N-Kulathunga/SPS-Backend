package com.it.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

import com.it.sps.dto.SchedulerApplicationDto;

import com.it.sps.entity.Application;
import com.it.sps.entity.ApplicationPK;

public interface ApplicationRepository extends JpaRepository<Application,ApplicationPK>{

		 @Query(" SELECT a FROM Application a WHERE a.applicationNo=:applicationNo ")
		 Application findAllWithApplicant(@Param("applicationNo")String applicationNo);

		 @Query("SELECT a FROM Application a")
		 List<Application> findAllApplications();

		 @Query("SELECT a FROM Application a JOIN FETCH a.applicant WHERE a.id = :id")
		 Application findWithApplicantById(@Param("id") ApplicationPK id);

		// Optional<ApplicationModel> findById_ApplicationId(String applicationId);
		 @Query(" SELECT a FROM Application a WHERE a.id.applicationId=:applicationId ")
		 Optional<Application> findByApplicationId(String applicationId);

	    @Query("SELECT a.applicationNo FROM Application a")
	    List<String> findAllApplicationNos();

	    @Query("SELECT COUNT(a) > 0 FROM Application a WHERE a.applicationNo = :applicationNo")
	    boolean existsByApplicationNo(String applicationNo);
		@Query("SELECT a FROM Application a " +
            "WHERE a.id.deptId = :deptId AND a.applicationType = :applicationType")
    	List<Application> findByDeptIdAndType(@Param("deptId") String deptId,
                                          @Param("applicationType") String applicationType);



    // with departent id and application type
//        @Query("SELECT new com.it.sps.dto.SchedulerApplicationDto(a.id.applicationId, a.id.deptId) " +
//                "FROM Application a " +
//                "WHERE a.id.deptId = :deptId AND a.applicationType = :applicationType")
//        List<SchedulerApplicationDto> findApplicationsByDeptAndType(
//                @Param("deptId") String deptId,
//                @Param("applicationType") String applicationType
//        );

		@Query("SELECT a FROM Application a " +
				"WHERE a.id.deptId = :deptId " +
				"AND a.applicationType = :applicationType " +
				"AND a.status = :status")
		List<Application> findApplicationsByDeptTypeStatus(@Param("deptId") String deptId,
													   @Param("applicationType") String applicationType,
													   @Param("status") String status);
}
