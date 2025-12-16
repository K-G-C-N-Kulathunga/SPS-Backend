package com.it.sps.service;

import com.it.sps.dto.PivGenerateDto;
import com.it.sps.entity.Applicant;
import com.it.sps.entity.Application;
import com.it.sps.repository.PivGenerateRepository;
import java.util.Optional;
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
