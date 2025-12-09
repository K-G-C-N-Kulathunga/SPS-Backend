package com.it.sps.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.it.sps.dto.ApplicationSubTypeDropDownDTO;
import com.it.sps.entity.ApplicationSubType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationSubTypeRepository extends JpaRepository<ApplicationSubType, String> {

    @Query("SELECT new com.it.sps.dto.ApplicationSubTypeDropDownDTO(a.appSubTypeCode) " +
            "FROM ApplicationSubType a " +
            "WHERE a.appTypeCode = :type " +
            "AND a.status = 1 " +
            "ORDER BY a.orderKey DESC")
    List<ApplicationSubTypeDropDownDTO> findForDropdown(@Param("type") String type);

}
