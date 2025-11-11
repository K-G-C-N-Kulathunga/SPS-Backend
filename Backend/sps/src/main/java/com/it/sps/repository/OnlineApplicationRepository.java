package com.it.sps.repository;

import com.it.sps.entity.OnlineApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OnlineApplicationRepository extends JpaRepository<OnlineApplication, String> {

    @Query("SELECT MAX(SUBSTRING(a.tempId, LENGTH(a.tempId) - 2, 3)) " +
            "FROM OnlineApplication a " +
            "WHERE SUBSTRING(a.tempId, 1, 10) = :mobile AND SUBSTRING(a.tempId, 11, 2) = :year")

    String findMaxSerialByMobileAndYear(@Param("mobile") String mobile, @Param("year") String year);

    boolean existsByTempId(String tempId);

}
