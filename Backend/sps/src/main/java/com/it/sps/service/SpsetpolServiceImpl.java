package com.it.sps.service;

import com.it.sps.dto.SpsetpolDto;
import com.it.sps.entity.Spsetpol;
import com.it.sps.entity.SpsetpolPK;
import com.it.sps.repository.SpsetpolRepository;
import com.it.sps.repository.InmatmRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SpsetpolServiceImpl implements SpsetpolService {

    private final SpsetpolRepository repository;
    private final InmatmRepository inmatmRepository;

    public SpsetpolServiceImpl(SpsetpolRepository repository, InmatmRepository inmatmRepository) {
        this.repository = repository;
        this.inmatmRepository = inmatmRepository;
    }

    @Override
    public List<Spsetpol> saveAll(String applicationNo, String deptId, List<SpsetpolDto> items) {
        if (applicationNo == null || applicationNo.isBlank())
            throw new IllegalArgumentException("applicationNo is required");
        if (deptId == null || deptId.isBlank())
            throw new IllegalArgumentException("deptId is required");

        // Validate referenced materials exist to avoid FK failures (handle padded
        // MAT_CD)
        java.util.Map<String, String> canonicalCodes = new java.util.HashMap<>(); // trimmed -> exact stored matCd
        if (items != null && !items.isEmpty()) {
            java.util.Set<String> missing = new java.util.HashSet<>();
            for (SpsetpolDto it : items) {
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
                throw new IllegalArgumentException("Invalid material codes for SPSETPOL (not in INMATM): " + missing);
            }
        }

        repository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);

        List<Spsetpol> list = new ArrayList<>();
        if (items != null) {
            for (SpsetpolDto it : items) {
                Spsetpol entity = new Spsetpol();
                String trimmed = it.getMatCd() == null ? null : it.getMatCd().trim();
                String canonical = trimmed == null ? null : canonicalCodes.get(trimmed);
                SpsetpolPK pk = new SpsetpolPK(
                        applicationNo,
                        deptId,
                        it.getPointType(),
                        it.getPoleType(),
                        it.getFromConductor(),
                        it.getToConductor(),
                        canonical != null ? canonical : it.getMatCd());
                entity.setId(pk);
                entity.setMatQty(it.getMatQty());
                entity.setJobCategoryId(it.getJobCategoryId());
                entity.setDescription(it.getDescription());
                list.add(repository.save(entity));
            }
        }
        return list;
    }

    @Override
    public List<Spsetpol> findByApplicationAndDept(String applicationNo, String deptId) {
        return repository.findByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }

    @Override
    public void deleteByApplicationAndDept(String applicationNo, String deptId) {
        repository.deleteByIdApplicationNoAndIdDeptId(applicationNo, deptId);
    }
}
