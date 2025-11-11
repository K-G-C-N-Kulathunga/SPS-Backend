package com.it.sps.repository;

import com.it.sps.dto.MaterialDTO;
import com.it.sps.entity.Spestmtm;
import com.it.sps.entity.SpestmtmPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpestmtmRepository extends JpaRepository<Spestmtm, SpestmtmPK> {

	@Query("SELECT new com.it.sps.dto.MaterialDTO(m.matNm, m.unitPrice, m.majUom, s.id.matCd) " +
			"FROM Spestmtm s " +
			"JOIN s.inmatm m " +
			"JOIN Inwrhmtm w ON s.id.matCd = w.id.matCd AND w.id.deptId = s.id.deptId " +
			"WHERE s.id.deptId = :deptId " +
			"AND s.id.connectionType = :connectionType " +
			"AND w.status = 2"+
			"AND s.id.wiringType = :wiringType " +
			"AND s.id.phase = :phase " +
			"ORDER BY s.id.matCd")
    List<MaterialDTO> findMaterialsByCriteria(
            @Param("deptId") String deptId,
            @Param("connectionType") long connectionType,
            @Param("wiringType") String wiringType,
            @Param("phase") long phase);
}