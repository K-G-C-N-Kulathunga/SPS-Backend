package com.it.sps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "DOCUMENT_UPLOAD")
public class DocumentUpload {

    @Id
    @Column(name = "DOC_ID", length = 10)
    private String docId;

    @Column(name = "TEMP_ID", nullable = false, length = 50)
    private String tempId;

    @Column(name = "DOCPATH")
    private String docpath;

    @Column(name = "UPLOADED_DATE")
    private LocalDate uploadedDate;
}
