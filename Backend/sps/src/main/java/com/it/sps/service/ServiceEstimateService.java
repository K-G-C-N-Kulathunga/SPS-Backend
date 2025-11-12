package com.it.sps.service;

import com.it.sps.dto.ServiceEstimateDto;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpSetWir;
import java.util.List;
import java.util.Map;

public interface ServiceEstimateService {
    ServiceEstimateResult saveServiceEstimate(ServiceEstimateDto dto);
    ServiceEstimateResult saveFromFrontendData(Map<String, Object> frontendData);
    
    public static class ServiceEstimateResult {
        private SpsErest spsErest;
        private List<SpSetWir> spSetWirList;
        
        public ServiceEstimateResult(SpsErest spsErest, List<SpSetWir> spSetWirList) {
            this.spsErest = spsErest;
            this.spSetWirList = spSetWirList;
        }
        
        // Getters
        public SpsErest getSpsErest() { return spsErest; }
        public List<SpSetWir> getSpSetWirList() { return spSetWirList; }
    }
}
