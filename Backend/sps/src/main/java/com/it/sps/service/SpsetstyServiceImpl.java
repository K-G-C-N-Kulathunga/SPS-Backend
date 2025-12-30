package com.it.sps.service;

import com.it.sps.dto.SpsetstyDto;
import com.it.sps.entity.Spsetsty;
import com.it.sps.entity.SpsetstyPK;
import com.it.sps.repository.SpsetstyRepository;
import com.it.sps.repository.InmatmRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SpsetstyServiceImpl implements SpsetstyService {

    private final SpsetstyRepository repository;
    private final InmatmRepository inmatmRepository;

    public SpsetstyServiceImpl(SpsetstyRepository repository, InmatmRepository inmatmRepository) {
        this.repository = repository;
        this.inmatmRepository = inmatmRepository;
    }

    @Override
    public List<Spsetsty> saveAll(String applicationNo, String deptId, List<SpsetstyDto> items) {
        if (applicationNo == null || applicationNo.isBlank())
            throw new IllegalArgumentException("applicationNo is required");
        if (deptId == null || deptId.isBlank())
            throw new IllegalArgumentException("deptId is required");

        // Validate materials exist (handle padded MAT_CD)
        java.util.Map<String, String> canonicalCodes = new java.util.HashMap<>(); // trimmed -> exact stored matCd
        if (items != null && !items.isEmpty()) {
            java.util.Set<String> missing = new java.util.HashSet<>();
            for (SpsetstyDto it : items) {
                String raw = it.getMatCd();
                String trimmed = raw == null ? null : raw.trim();
                if (trimmed == null || trimmed.isBlank()) {
                    missing.add(String.valueOf(raw));
                    continue;
                }
                com.it.sps.entity.Inmatm m = inmatmRepository.findByTrimmedMatCd(trimmed);
                if (m == null) {
                    missing.add(trimmed);
                } else {
                    canonicalCodes.put(trimmed, m.getMatCd());
                }
            }
            if (!missing.isEmpty()) {
                throw new IllegalArgumentException("Invalid material codes for SPSETSTY (not in INMATM): " + missing);
            }
        }

        repository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);

        List<Spsetsty> list = new ArrayList<>();
        if (items != null) {
            for (SpsetstyDto it : items) {
                Spsetsty entity = new Spsetsty();
                String trimmed = it.getMatCd() == null ? null : it.getMatCd().trim();
                String canonical = trimmed == null ? null : canonicalCodes.get(trimmed);
                entity.setId(new SpsetstyPK(applicationNo, deptId, canonical != null ? canonical : it.getMatCd(),
                        it.getStayType()));
                entity.setMatQty(it.getMatQty());
                list.add(repository.save(entity));
            }
        }
        return list;
    }

    @Override
    public List<Spsetsty> findByApplicationAndDept(String applicationNo, String deptId) {
        return repository.findByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }

    @Override
    public void deleteByApplicationAndDept(String applicationNo, String deptId) {
        repository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }
}
