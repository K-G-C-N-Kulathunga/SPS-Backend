package com.it.sps.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.it.sps.dto.ApplicationMaterialDto;
import com.it.sps.dto.AvailableLabourDTO;
import com.it.sps.dto.AvailableMaterial;
import com.it.sps.dto.ErrorResponse;
import com.it.sps.dto.MaterialDTO;
import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.entity.Inmatm;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.AvailablelabourRepository;
import com.it.sps.repository.InwrhmtmRepository;
import com.it.sps.service.MaterialService;

@RestController
@RequestMapping("/api")
public class MaterialController {
	
	private final MaterialService inmatmService;
	private final InwrhmtmRepository inwrhmtmRepository;
	private final ApplicationRepository applicationRepository;
	private final AvailablelabourRepository availablelabourRepository; 
    @Autowired
    public MaterialController(MaterialService inmatmService, InwrhmtmRepository inwrhmtmRepository,ApplicationRepository applicationRepository , AvailablelabourRepository availablelabourRepository) {
        this.inmatmService = inmatmService;
		this.inwrhmtmRepository = inwrhmtmRepository;
		this.applicationRepository = applicationRepository;
		this.availablelabourRepository = availablelabourRepository;
    }

    @GetMapping("/materials")
    public List<Inmatm> getMaterialsByName(@RequestParam String name) {
        return inmatmService.getMaterialsByName(name);
    }
    
//    @GetMapping(value ="/getDetails", produces = "application/json")
//    public List<MaterialDTO> getMaterials(
//
//            @RequestParam String deptId,
//            @RequestParam long connectionType,
//            @RequestParam String wiringType,
//            @RequestParam long phase) {
//        return inmatmService.getMaterials(deptId, connectionType, wiringType, phase);
//
//    }
    
    @GetMapping(value ="/getDetails", produces = "application/json")
    public ResponseEntity<?> getMaterials(
        @RequestParam String deptId,
        @RequestParam long connectionType,
        @RequestParam String wiringType,
        @RequestParam long phase) {
        
        try {
            List<MaterialDTO> materials = inmatmService.getMaterials(deptId, connectionType, wiringType, phase);
            return ResponseEntity.ok(materials);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid input parameters", e.getMessage()));
        } catch (DataAccessException e) {
            // Handle database-related errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Database error", "Unable to retrieve materials data"));
        } catch (Exception e) {
            // Handle any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Unexpected error", e.getMessage()));
        }
    }
    
    @GetMapping("/available")
    public List <AvailableMaterial> findavailableMaterial (@RequestParam String deptId){
    	return inwrhmtmRepository.findavailableMaterial(deptId);
    }
    
    @GetMapping(value ="/getAvailableDetails", produces = "application/json")
    public List<AvailableMaterial> getAvailableMaterials(

            @RequestParam String deptId,
            @RequestParam long connectionType,
            @RequestParam String wiringType,
            @RequestParam long phase) {
    	
    	List<MaterialDTO> defaultMaterials =  inmatmService.getMaterials(deptId, connectionType, wiringType, phase);
    	
        List<AvailableMaterial> availableMaterials = inwrhmtmRepository.findavailableMaterial(deptId);
        
       
        Set<String> defaultMaterialNames = defaultMaterials.stream()
                .map(MaterialDTO::getMaterialName) 
                .collect(Collectors.toSet());

        // Filter out the default materials from the available materials
        return availableMaterials.stream()
                .filter(material -> !defaultMaterialNames.contains(material.getMaterialName())) 
                .collect(Collectors.toList());

    }
    
    @GetMapping("/by-applicationNo")
    public ApplicationMaterialDto getApplication(@RequestParam String applicationNo) {
    	ApplicationMaterialDto applicationDTO = new ApplicationMaterialDto();
    	
   	try {
   		if(applicationNo==null ||applicationNo.trim().isEmpty()) {
   	 throw new IllegalArgumentException("Application number cannot be empty");
   		}
   		if(applicationNo.length()>50) {
   			throw new IllegalArgumentException("Application number cannot exceed 10");
   		}
   	 Application application=  applicationRepository.findAllWithApplicant(applicationNo);
         if(application==null) {
       	  throw new IllegalArgumentException("No applications found");
         }
         Applicant applicant= application.getApplicant();
   
         applicationDTO.setDeptId(applicant.getDeptId());
         applicationDTO.setConnectionType(60);
         applicationDTO.setPhase(3);
         applicationDTO.setWiringType("OH");


   	
   	}catch (IllegalArgumentException e) {
   		e.printStackTrace();
   		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
   				"An unexpected error occured while processing the application",e);
   	}
         	          return applicationDTO;
   	
   }
    
 
    		@GetMapping("/labours")
    		public List<AvailableLabourDTO>getAvailablelab(@RequestParam String deptId){
    			try {
    			List<AvailableLabourDTO> availableLabourDTO=availablelabourRepository.findAvailableLabour(deptId);
    			return availableLabourDTO;

    			} catch(IllegalArgumentException e) {
    				e.printStackTrace();
    				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
    		   				"An unexpected error occured while processing the application",e);
    			}

    		}
    	     

}
