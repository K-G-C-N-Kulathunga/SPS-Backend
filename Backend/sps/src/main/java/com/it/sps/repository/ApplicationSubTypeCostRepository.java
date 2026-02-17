package com.it.sps.repository;

import com.it.sps.entity.ApplicationSubTypeCost;
import com.it.sps.entity.ApplicationSubTypeCostId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationSubTypeCostRepository
        extends JpaRepository<ApplicationSubTypeCost, ApplicationSubTypeCostId> {

    List<ApplicationSubTypeCost>
    findByApplicationTypeAndApplicationSubTypeOrderByDisplayOrder(
            String applicationType,
            String applicationSubType
    );
}
