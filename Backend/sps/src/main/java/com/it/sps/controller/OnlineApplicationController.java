package com.it.sps.controller;

import com.it.sps.dto.AddressDetailsDto;
import com.it.sps.entity.OnlineApplication;
import com.it.sps.service.OnlineApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/online-applications")
public class OnlineApplicationController {

    @Autowired
    private OnlineApplicationService onlineApplicationService;

    @PostMapping
    public OnlineApplication create(@RequestBody OnlineApplication application) {
        return onlineApplicationService.createApplication(application);
    }

    @PutMapping("/{tempId}")
    @Transactional
    public ResponseEntity<OnlineApplication> updateApplication(@PathVariable String tempId, @RequestBody OnlineApplication application) {
 //       try {
            OnlineApplication updated = onlineApplicationService.updateApplication(tempId, application);
            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
    }



    //get the addres details in the service location details
    @GetMapping("/{tempId}/address")
    public ResponseEntity<AddressDetailsDto> getAddressDetails(@PathVariable String tempId) {
        AddressDetailsDto addressDetails = onlineApplicationService.getAddressDetails(tempId);
        return ResponseEntity.ok(addressDetails);
    }

    @GetMapping("/generate-tempId")
    public ResponseEntity<String> generateTempId(@RequestParam String mobile) {
        String tempId = onlineApplicationService.generateTempId(mobile);
        return ResponseEntity.ok(tempId);
    }


    @GetMapping("/{tempId}")
    public ResponseEntity<OnlineApplication> getApplicationByTempId(@PathVariable String tempId) {
        OnlineApplication application = onlineApplicationService.getApplicationByTempId(tempId);
        if (application != null) {
            return ResponseEntity.ok(application);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{tempId}/service-location-details")
    public ResponseEntity<AddressDetailsDto> getServiceLocationDetailsWithAreaAndCSC(@PathVariable String tempId) {
        AddressDetailsDto details = onlineApplicationService.getServiceLocationDetailsWithAreaAndCSC(tempId);
        return ResponseEntity.ok(details);
    }

}
