package com.it.sps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.it.sps.repository.UserroleActivityRepository;

@Service
public class UserroleActService {
	
	private final UserroleActivityRepository userroleActivityRepository;

	@Autowired
	public UserroleActService(UserroleActivityRepository userroleActivityRepository) {
		this.userroleActivityRepository = userroleActivityRepository;
	}
	
	   public List<String> getActivityDescriptionsByRoleName(String roleName) {
	        return userroleActivityRepository.findActivityDescriptionByRoleType(roleName);
	    }

}
