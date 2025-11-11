package com.it.sps.service;

import com.it.sps.dto.SpsErestDto;
import com.it.sps.entity.Application;
import com.it.sps.entity.SpsErest;
import com.it.sps.entity.SpsErestPK;
import com.it.sps.repository.ApplicationRepository;
import com.it.sps.repository.SpsErestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class SpsErestServiceImpl implements SpsErestService {

    private final ApplicationRepository applicationRepository;
    private final SpsErestRepository spsErestRepository;

    public SpsErestServiceImpl(ApplicationRepository applicationRepository,
                               SpsErestRepository spsErestRepository) {
        this.applicationRepository = applicationRepository;
        this.spsErestRepository = spsErestRepository;
    }

    @Override
    public SpsErest save(SpsErestDto dto) {
        // 1) Validate PK parts exist
        if (dto.getApplicationNo() == null || dto.getApplicationNo().isBlank()) {
            throw new IllegalArgumentException("applicationNo is required");
        }
        if (dto.getDeptId() == null || dto.getDeptId().isBlank()) {
            throw new IllegalArgumentException("deptId is required");
        }

        // 2) Ensure Application exists (you asked to fetch & save its Application_No)
        System.out.println("DEBUG: SpsErestService - Looking for application with applicationNo: " + dto.getApplicationNo());
        Application app = applicationRepository.findAllWithApplicant(dto.getApplicationNo());
        System.out.println("DEBUG: SpsErestService - Found application: " + app);
        if (app == null) {
            throw new IllegalStateException("Application not found with applicationNo=" + dto.getApplicationNo());
        }

        // 3) Build PK
        SpsErestPK pk = new SpsErestPK(dto.getApplicationNo(), dto.getDeptId());

        // 4) Upsert (create or update)
        SpsErest entity = spsErestRepository.findById(pk).orElseGet(() -> {
            SpsErest e = new SpsErest();
            e.setId(pk);
            return e;
        });

        // 5) Map all requested columns
        entity.setWiringType(dto.getWiringType());
        entity.setSecondCircuitLength(nz(dto.getSecondCircuitLength(), bd(0)));
        entity.setTotalLength(dto.getTotalLength());
        entity.setCableType(dto.getCableType());
        entity.setConversionLength(nz(dto.getConversionLength(), bd(0)));
        entity.setConversionLength2p(nz(dto.getConversionLength2p(), bd(0)));
        entity.setLoopCable(dto.getLoopCable());
        entity.setDistanceToSp(dto.getDistanceToSp());
        entity.setSin(dto.getSin());
        entity.setIsSyaNeeded(dto.getIsSyaNeeded());
        entity.setBusinessType(dto.getBusinessType());
        entity.setNoOfSpans(dto.getNoOfSpans());
        entity.setIsStandardVc(nz(dto.getIsStandardVc(), "Y")); // DB default is 'Y'
        entity.setPoleno(dto.getPoleno());
        entity.setDistanceFromSs(dto.getDistanceFromSs());
        entity.setSubstation(dto.getSubstation());
        entity.setTransformerCapacity(dto.getTransformerCapacity());
        entity.setTransformerLoad(dto.getTransformerLoad());
        entity.setTransformerPeakLoad(dto.getTransformerPeakLoad());
        entity.setFeederControlType(dto.getFeederControlType());
        entity.setPhase(dto.getPhase());
        entity.setInsideLength(dto.getInsideLength());
        // Note: If you want Oracle defaults to apply, either set defaults here (as done)
        // or mark columns with @Column(insertable=false) in the entity.

        return spsErestRepository.save(entity);
    }

    @Override
    public SpsErest findOne(String applicationNo, String deptId) {
        SpsErestPK pk = new SpsErestPK(applicationNo, deptId);
        return spsErestRepository.findById(pk)
                .orElseThrow(() -> new IllegalStateException("SpsErest not found for appNo=" + applicationNo + ", deptId=" + deptId));
    }

    @Override
    public void delete(String applicationNo, String deptId) {
        SpsErestPK pk = new SpsErestPK(applicationNo, deptId);
        spsErestRepository.deleteById(pk);
    }

    // --- helpers ---
    private static BigDecimal bd(int v) { return new BigDecimal(v); }
    private static BigDecimal nz(BigDecimal in, BigDecimal def) { return in == null ? def : in; }
    private static String nz(String in, String def) { return (in == null || in.isBlank()) ? def : in; }
}
