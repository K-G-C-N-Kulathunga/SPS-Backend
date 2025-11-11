package com.it.sps.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.it.sps.entity.Sauserm;

@Repository
public interface SausermRepository extends JpaRepository<Sauserm, String> {
	
	@Query("SELECT s FROM Sauserm s WHERE TRIM(UPPER(s.userId)) = TRIM(UPPER(:userId))")
    Sauserm findByUserIdIgnoreCase(@Param("userId") String userId);

}