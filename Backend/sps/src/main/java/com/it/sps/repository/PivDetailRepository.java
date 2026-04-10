package com.it.sps.repository;

import com.it.sps.entity.PivDetail;
import com.it.sps.entity.PivDetailId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PivDetailRepository extends JpaRepository<PivDetail, PivDetailId> {

    @Query(value = """
        SELECT NVL(MAX(TO_NUMBER(REGEXP_SUBSTR(PIV_NO, '[^/]+$', 1, 1))), 0)
        FROM PIV_DETAIL
        WHERE DEPT_ID = :deptId
          AND PIV_NO LIKE ('PIV/' || :deptId || '/' || :typeCd || '/' || :year || '/%')
        """, nativeQuery = true)
    Long findMaxRunningNo(
            @Param("deptId") String deptId,
            @Param("typeCd") String typeCd,
            @Param("year") String year
    );

    @Modifying
    @Query(value = """
        INSERT INTO PIV_DETAIL
        (PIV_NO, PIV_SEQ_NO, DEPT_ID, REFERENCE_TYPE, REFERENCE_NO,
         ID_NO, PIV_AMOUNT, GRAND_TOTAL, PREPARED_BY, STATUS,
         EST_REFERENCE_NO, TITLE_CD, PIV_DATE, ADD_USER, ADD_DATE)
        VALUES
        (:pivNo, :pivSeqNo, :deptId, :referenceType, :referenceNo,
         :idNo, :pivAmount, :grandTotal, :preparedBy, :status,
         :estReferenceNo, :titleCd, SYSDATE, :addUser, SYSDATE)
        """, nativeQuery = true)
    void savePivDetail(
            @Param("pivNo")         String pivNo,
            @Param("pivSeqNo")      BigDecimal pivSeqNo,
            @Param("deptId")        String deptId,
            @Param("referenceType") String referenceType,
            @Param("referenceNo")   String referenceNo,
            @Param("idNo")          String idNo,
            @Param("pivAmount")     BigDecimal pivAmount,
            @Param("grandTotal")    BigDecimal grandTotal,
            @Param("preparedBy")    String preparedBy,
            @Param("status")        String status,
            @Param("estReferenceNo") String estReferenceNo,
            @Param("titleCd")       String titleCd,
            @Param("addUser")       String addUser
    );
}