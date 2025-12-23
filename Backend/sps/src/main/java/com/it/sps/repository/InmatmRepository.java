package com.it.sps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.sps.entity.Inmatm;



public interface InmatmRepository extends JpaRepository<Inmatm,String>{
	
	List<Inmatm> findByMatNm(@RequestParam String name);	

}
