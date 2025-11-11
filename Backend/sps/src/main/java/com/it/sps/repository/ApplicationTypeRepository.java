package com.it.sps.repository;

import com.it.sps.entity.ApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long> {
    // You can add custom query methods here if needed
}
