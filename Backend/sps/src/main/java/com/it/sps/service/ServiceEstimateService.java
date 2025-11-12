package com.it.sps.service;

import com.it.sps.dto.ServiceEstimateDto;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpSetWir;
import com.it.sps.entity.Spsetpol;
import com.it.sps.entity.Spsetstu;
import com.it.sps.entity.Spsetsty;
import java.util.List;
import java.util.Map;

public interface ServiceEstimateService {
    ServiceEstimateResult saveServiceEstimate(ServiceEstimateDto dto);

    ServiceEstimateResult saveFromFrontendData(Map<String, Object> frontendData);

    public static class ServiceEstimateResult {
        private SpsErest spsErest;
        private List<SpSetWir> spSetWirList;
        // optional: include sketch4 saved rows
        private List<Spsetpol> spsetpolList;
        private List<Spsetstu> spsetstuList;
        private List<Spsetsty> spsetstyList;

        public ServiceEstimateResult(SpsErest spsErest, List<SpSetWir> spSetWirList) {
            this.spsErest = spsErest;
            this.spSetWirList = spSetWirList;
        }

        // Getters
        public SpsErest getSpsErest() {
            return spsErest;
        }

        public List<SpSetWir> getSpSetWirList() {
            return spSetWirList;
        }

        public List<Spsetpol> getSpsetpolList() {
            return spsetpolList;
        }

        public void setSpsetpolList(List<Spsetpol> spsetpolList) {
            this.spsetpolList = spsetpolList;
        }

        public List<Spsetstu> getSpsetstuList() {
            return spsetstuList;
        }

        public void setSpsetstuList(List<Spsetstu> spsetstuList) {
            this.spsetstuList = spsetstuList;
        }

        public List<Spsetsty> getSpsetstyList() {
            return spsetstyList;
        }

        public void setSpsetstyList(List<Spsetsty> spsetstyList) {
            this.spsetstyList = spsetstyList;
        }
    }
}
