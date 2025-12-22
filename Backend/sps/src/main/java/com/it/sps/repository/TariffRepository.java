package com.it.sps.repository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.it.sps.dto.ApplicationTariffCodeDropDownDTO;
import com.it.sps.entity.Tariff;

public interface TariffRepository extends JpaRepository<Tariff, String> {

    @Query("Select new com.it.sps.dto.ApplicationTariffCodeDropDownDTO(a.tariffCode) " +
            "FROM Tariff a " +
            "WHERE a.isSmcActive = '1'")
    List<ApplicationTariffCodeDropDownDTO>  findActiveTariffCode();
}