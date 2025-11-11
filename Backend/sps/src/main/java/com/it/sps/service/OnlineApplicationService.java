package com.it.sps.service;

import com.it.sps.dto.AddressDetailsDto;
import com.it.sps.entity.OnlineApplication;



public interface OnlineApplicationService {
    OnlineApplication createApplication(OnlineApplication application);
    OnlineApplication updateApplication(String tempId, OnlineApplication updatedApplication);

    AddressDetailsDto getAddressDetails(String tempId);

    public String generateTempId(String mobile);

    OnlineApplication getApplicationByTempId(String tempId);

    AddressDetailsDto getServiceLocationDetailsWithAreaAndCSC(String tempId);

}
