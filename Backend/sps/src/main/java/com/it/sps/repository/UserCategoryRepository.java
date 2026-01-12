package com.it.sps.repository;

import com.it.sps.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, String> {
    // Optional helpers:
    boolean existsByUserIdIgnoreCase(String userId);
    UserCategory findByUserIdIgnoreCase(String userId);
}
