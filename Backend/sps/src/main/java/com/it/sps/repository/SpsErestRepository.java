package com.it.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpsErestPK;

public interface SpsErestRepository extends JpaRepository<SpsErest, SpsErestPK> {
}
