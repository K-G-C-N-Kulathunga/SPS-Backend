package com.it.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.it.sps.entity.PivCostItem;

@Repository
public interface PivCostItemRepository extends JpaRepository<PivCostItem, Long> {
}
