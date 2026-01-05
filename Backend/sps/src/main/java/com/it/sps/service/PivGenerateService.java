package com.it.sps.service;

import com.it.sps.dto.PivChargeDto;
import com.it.sps.dto.PivGenerateDto;
import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.repository.PivGenerateRepository;
import com.it.sps.repository.projection.PivAccountProjection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PivGenerateService {

    private final PivGenerateRepository pivGenerateRepository;

    public PivGenerateService(PivGenerateRepository pivGenerateRepository) {
        this.pivGenerateRepository = pivGenerateRepository;
    }

    public Optional<PivGenerateDto> getCustomerDetails(String applicationNo) {
        if (!hasText(applicationNo)) {
            return Optional.empty();
        }

        Optional<Application> optionalApplication = pivGenerateRepository.findByApplicationNoWithApplicant(applicationNo.trim());
        if (optionalApplication.isEmpty()) {
            return Optional.empty();
        }

        Application application = optionalApplication.get();
        Applicant applicant = application.getApplicant();
        if (applicant == null) {
            return Optional.empty();
        }

        PivGenerateDto dto = new PivGenerateDto();
        dto.setEstimationNo(application.getApplicationNo());
        dto.setCustomerId(applicant.getIdNo());
        dto.setCustomerName(join(" ", applicant.getFirstName(), applicant.getLastName()));
        dto.setAddress(join(", ", applicant.getStreetAddress(), applicant.getSuburb(), applicant.getCity()));
        dto.setTelephone(applicant.getTelephoneNo());
        dto.setMobile(applicant.getMobileNo());
        dto.setEmail(applicant.getEmail());
        return Optional.of(dto);
    }

    public List<PivChargeDto> getChargeAccounts(String titleCd) {
        String resolvedTitle = hasText(titleCd) ? titleCd.trim() : "PIV-SVC";
        List<PivAccountProjection> projections = pivGenerateRepository.findChargeAccounts(resolvedTitle);
        return projections.stream()
                .map(this::mapChargeProjection)
                .collect(Collectors.toList());
    }

    private PivChargeDto mapChargeProjection(PivAccountProjection projection) {
        PivChargeDto dto = new PivChargeDto();
        dto.setCodeNo(projection.getCodeNo());
        dto.setDescription(projection.getDescription());
        return dto;
    }

    private String join(String delimiter, String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (hasText(part)) {
                if (builder.length() > 0) {
                    builder.append(delimiter);
                }
                builder.append(part.trim());
            }
        }
        return builder.length() > 0 ? builder.toString() : null;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
