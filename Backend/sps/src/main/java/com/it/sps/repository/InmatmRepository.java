package com.it.sps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.sps.entity.Inmatm;

public interface InmatmRepository extends JpaRepository<Inmatm, String> {

	List<Inmatm> findByMatNm(@RequestParam String name);

	// Resolve a material by trimmed code, to handle padded MAT_CD values in
	// database
	@Query("SELECT i FROM Inmatm i WHERE TRIM(i.matCd) = :trimmed")
	Inmatm findByTrimmedMatCd(@Param("trimmed") String trimmed);

}
