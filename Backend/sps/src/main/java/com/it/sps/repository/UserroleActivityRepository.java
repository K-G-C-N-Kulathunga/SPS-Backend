package com.it.sps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.it.sps.entity.UserroleActivity;
import com.it.sps.entity.UserroleActivityPK;


public interface UserroleActivityRepository extends JpaRepository<UserroleActivity,UserroleActivityPK>{
	
	 @Query("SELECT a.description FROM UserroleActivity ua " +
	           "JOIN ua.activity a " +
	           "JOIN ua.userrole ur " +
	           "WHERE ur.roleType = :roleType")
	List<String> findActivityDescriptionByRoleType(@Param("roleType") String roleType);
}
