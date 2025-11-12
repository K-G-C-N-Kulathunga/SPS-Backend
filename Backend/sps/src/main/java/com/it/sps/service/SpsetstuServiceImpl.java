package com.it.sps.service;

import com.it.sps.dto.SpsetstuDto;
import com.it.sps.entity.Spsetstu;
import com.it.sps.entity.SpsetstuPK;
import com.it.sps.repository.SpsetstuRepository;
import com.it.sps.repository.InmatmRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SpsetstuServiceImpl implements SpsetstuService {

    private final SpsetstuRepository repository;
    private final InmatmRepository inmatmRepository;

    public SpsetstuServiceImpl(SpsetstuRepository repository, InmatmRepository inmatmRepository) {
        this.repository = repository;
        this.inmatmRepository = inmatmRepository;
    }

    @Override
    public List<Spsetstu> saveAll(String applicationNo, String deptId, List<SpsetstuDto> items) {
        if (applicationNo == null || applicationNo.isBlank())
            throw new IllegalArgumentException("applicationNo is required");
        if (deptId == null || deptId.isBlank())
            throw new IllegalArgumentException("deptId is required");

        // Validate materials exist (handle padded MAT_CD)
        java.util.Map<String, String> canonicalCodes = new java.util.HashMap<>(); // trimmed -> exact stored matCd
        if (items != null && !items.isEmpty()) {
            java.util.Set<String> missing = new java.util.HashSet<>();
            for (SpsetstuDto it : items) {
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
                throw new IllegalArgumentException("Invalid material codes for SPSETSTU (not in INMATM): " + missing);
            }
        }

        repository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);

        List<Spsetstu> list = new ArrayList<>();
        if (items != null) {
            for (SpsetstuDto it : items) {
                Spsetstu entity = new Spsetstu();
                String trimmed = it.getMatCd() == null ? null : it.getMatCd().trim();
                String canonical = trimmed == null ? null : canonicalCodes.get(trimmed);
                entity.setId(new SpsetstuPK(applicationNo, deptId, canonical != null ? canonical : it.getMatCd()));
                entity.setMatQty(it.getMatQty());
                list.add(repository.save(entity));
            }
        }
        return list;
    }

    @Override
    public List<Spsetstu> findByApplicationAndDept(String applicationNo, String deptId) {
        return repository.findByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }

    @Override
    public void deleteByApplicationAndDept(String applicationNo, String deptId) {
        repository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }
}
