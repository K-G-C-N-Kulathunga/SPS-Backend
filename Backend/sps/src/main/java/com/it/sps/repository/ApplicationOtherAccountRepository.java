package com.it.sps.repository;

import com.it.sps.entity.ApplicationOtherAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationOtherAccountRepository extends JpaRepository<ApplicationOtherAccount, Long> {
    List<ApplicationOtherAccount> findById(String id);
    void deleteById(String id); // To replace old entries when saving
}
