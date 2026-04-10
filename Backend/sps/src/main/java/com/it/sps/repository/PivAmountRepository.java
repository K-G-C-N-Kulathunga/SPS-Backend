package com.it.sps.repository;

import com.it.sps.entity.PivAmount;
import com.it.sps.entity.PivAmountId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface PivAmountRepository extends JpaRepository<PivAmount, PivAmountId> {

    @Modifying
    @Query(value = """
        INSERT INTO PIV_AMOUNT
        (DEPT_ID, PIV_NO, ACCOUNT_CODE, AMOUNT)
        VALUES (:deptId, :pivNo, :accountCode, :amount)
        """, nativeQuery = true)
    void saveCharge(
            @Param("deptId")       String deptId,
            @Param("pivNo")        String pivNo,
            @Param("accountCode")  String accountCode,
            @Param("amount")       BigDecimal amount
    );
}