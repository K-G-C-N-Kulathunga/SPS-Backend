package com.it.sps.repository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.it.sps.dto.ApplicationTariffCategoryDropDownDTO;
import com.it.sps.entity.TariffCategory;

public interface TariffCategoryRepository extends JpaRepository<TariffCategory, String> {

    @Query("Select new com.it.sps.dto.ApplicationTariffCategoryDropDownDTO(a.tariffName,a.tariffCatCode) " +
            "FROM TariffCategory a " +
            "Where a.isSmcActive = '1'")
    List<ApplicationTariffCategoryDropDownDTO> findActiveTariffCategory();
}