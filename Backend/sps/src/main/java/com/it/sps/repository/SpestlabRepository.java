package com.it.sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.sps.entity.Spestlab;
import com.it.sps.entity.SpestlabPK;

public interface SpestlabRepository extends JpaRepository<Spestlab, SpestlabPK> {

}

