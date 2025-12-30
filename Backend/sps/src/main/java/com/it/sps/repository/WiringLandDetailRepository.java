package com.it.sps.repository;

import com.it.sps.entity.WiringLandDetail;
import com.it.sps.entity.WiringLandDetailPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WiringLandDetailRepository extends JpaRepository<WiringLandDetail, WiringLandDetailPK> {

    WiringLandDetail findByIdApplicationIdAndIdDeptId(String applicationId, String deptId);

    @Query(value = "SELECT * FROM wiring_land_detail wld WHERE wld.application_id = :applicationId AND ROWNUM = 1", nativeQuery = true)
    WiringLandDetail findFirstByApplicationIdNative(@Param("applicationId") String applicationId);

}
