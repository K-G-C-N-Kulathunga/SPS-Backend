package com.it.sps.repository;

import com.it.sps.entity.DocumentUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentUploadRepository extends JpaRepository<DocumentUpload, String> {
}
