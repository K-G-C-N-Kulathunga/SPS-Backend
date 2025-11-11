package com.it.sps.controller;

import com.it.sps.entity.DocumentUpload;
import com.it.sps.entity.OnlineApplication;
import com.it.sps.repository.DocumentUploadRepository;
import com.it.sps.repository.OnlineApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${upload.dir:uploads}")
    private String uploadDir;


    @Autowired
    private DocumentUploadRepository documentUploadRepository;

    @Autowired
    private OnlineApplicationRepository onlineApplicationRepository;

    @PostMapping
    public ResponseEntity<?> uploadMultipleFiles(
            @RequestParam(value = "idCopy", required = false) MultipartFile idCopy,
            @RequestParam(value = "ownershipCertificate", required = false) MultipartFile ownershipCertificate,
            @RequestParam(value = "gramaNiladhariCertificate", required = false) MultipartFile gramaNiladhariCertificate,
            @RequestParam(value = "threephChartedEngineerCertificate", required = false) MultipartFile threephChartedEngineerCertificate,
            @RequestParam("tempId") String tempId
    ) {
        System.out.println("==== Received POST /api/upload ====");
        System.out.println("tempId: " + tempId);
        System.out.println("idCopy: " + (idCopy != null ? idCopy.getOriginalFilename() : "null"));
        System.out.println("ownershipCertificate: " + (ownershipCertificate != null ? ownershipCertificate.getOriginalFilename() : "null"));
        System.out.println("gramaNiladhariCertificate: " + (gramaNiladhariCertificate != null ? gramaNiladhariCertificate.getOriginalFilename() : "null"));
        System.out.println("threephChartedEngineerCertificate: " + (threephChartedEngineerCertificate != null ? threephChartedEngineerCertificate.getOriginalFilename() : "null"));
        try {
            // Ensure uploadDir ends with the system separator
            if (uploadDir == null || uploadDir.isBlank()) {
                uploadDir = "uploads";
            }
            if (!uploadDir.endsWith(File.separator)) {
                uploadDir = uploadDir + File.separator;
            }

            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                System.out.println("Failed to create upload directory: " + uploadDir);
                return ResponseEntity.internalServerError().body("Failed to create upload directory.");
            } else {
                System.out.println("Upload directory exists: " + uploadDir);
            }

            saveFile(idCopy, tempId, "idCopy");
            saveFile(ownershipCertificate, tempId, "ownershipCertificate");
            saveFile(gramaNiladhariCertificate, tempId, "gramaNiladhariCertificate");
            saveFile(threephChartedEngineerCertificate, tempId, "threephChartedEngineerCertificate");

            System.out.println("All files processed successfully.");
            return ResponseEntity.ok("All files uploaded successfully.");
        } catch (IOException e) {
            System.out.println("Exception during file upload: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    private void saveFile(MultipartFile file, String tempId, String docType) throws IOException {
        if (file == null || file.isEmpty()) {
            System.out.println("No file provided for: " + docType);
            return;
        }
        String original = file.getOriginalFilename();
        String extension = original != null && original.contains(".")
                ? original.substring(original.lastIndexOf('.'))
                : "";
        String newFilename = tempId + "_" + docType + extension;
        // Ensure uploadDir uses correct separator and resolve path safely
        if (uploadDir == null || uploadDir.isBlank()) {
            uploadDir = "uploads" + File.separator;
        } else if (!uploadDir.endsWith(File.separator)) {
            uploadDir = uploadDir + File.separator;
        }
        Path filePath = Paths.get(uploadDir, newFilename);
        System.out.println("Saving file: " + newFilename + " to " + filePath);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        DocumentUpload documentUpload = new DocumentUpload();
        documentUpload.setDocId(UUID.randomUUID().toString().substring(0, 10));
        documentUpload.setTempId(tempId);
        documentUpload.setDocpath(filePath.toAbsolutePath().toString());
        documentUpload.setUploadedDate(LocalDate.now());
        documentUploadRepository.save(documentUpload);
        System.out.println("Saved document info to DB for: " + docType);
    }
}
