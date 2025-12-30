package com.it.sps.repository;

import com.it.sps.dto.ApplicantDTO;
import com.it.sps.entity.Applicant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicantRepository extends JpaRepository<Applicant, String> {

    // Make DTO fetch case-insensitive so that 9-digit IDs ending with either 'v' or 'V' match without altering DB values.
    @Query("SELECT new com.it.sps.dto.ApplicantDTO(a.idNo, a.idType, a.personalCorporate, a.firstName, " +
            "a.lastName, a.fullName, a.telephoneNo, a.email, a.mobileNo, a.city, a.suburb, a.streetAddress, a.postalCode," +
            "a.preferredLanguage) FROM Applicant a WHERE UPPER(a.idNo) = UPPER(?1)")
    ApplicantDTO findApplicantDTOByIdNo(String applicantId);

    // Entity fetch (case-insensitive derived query)
    Optional<Applicant> findByIdNoIgnoreCase(String idNo);
}