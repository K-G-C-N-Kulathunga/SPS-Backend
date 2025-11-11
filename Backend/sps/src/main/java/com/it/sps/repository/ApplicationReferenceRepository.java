package com.it.sps.repository;

import com.it.sps.entity.ApplicationReference;
import com.it.sps.entity.ApplicationReferencePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationReferenceRepository extends JpaRepository<ApplicationReference, ApplicationReferencePK> {
}
