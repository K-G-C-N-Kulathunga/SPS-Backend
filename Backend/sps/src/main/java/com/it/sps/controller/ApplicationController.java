package com.it.sps.controller;

import com.it.sps.dto.ApplicationDTO;
import com.it.sps.dto.FormDataDto;
import com.it.sps.entity.Application;
import com.it.sps.entity.DocumentUpload;
import com.it.sps.repository.DocumentUploadRepository;
import com.it.sps.service.ApplicationService;
import com.it.sps.service.ApplicationWiringLDService;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationWiringLDService applicationWiringLDService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DocumentUploadRepository documentUploadRepository;

    // --- OS-specific paths read from application.properties ---
    @Value("${upload.dir.windows:C:\\\\SPS\\\\uploads\\\\}")
    private String uploadDirWindows;

    @Value("${upload.dir.linux:/opt/sps/uploads/}")
    private String uploadDirLinux;

    @Value("${upload.dir.default:uploads/}")
    private String uploadDirDefault;

    // Resolved & normalized upload directory used by the app
    private Path uploadBasePath;

    @PostConstruct
    public void initUploadDir() {
        // Decide which property value to use based on OS
        String osName = System.getProperty("os.name", "").toLowerCase();
        String selected;
        if (osName.contains("windows")) {
            selected = uploadDirWindows;
        } else if (osName.contains("linux")) {
            selected = uploadDirLinux;
        }
        else {
            selected = uploadDirDefault;
        }

        // Normalize to absolute path (no need to force a trailing separator)
        uploadBasePath = Paths.get(selected).toAbsolutePath().normalize();

        // Ensure the directory exists
        File dir = uploadBasePath.toFile();
        if (!dir.exists() && !dir.mkdirs()) {
            // If creation fails, fall back to a safe relative "uploads" folder
            uploadBasePath = Paths.get("uploads").toAbsolutePath().normalize();
            uploadBasePath.toFile().mkdirs();
        }

        System.out.println("Upload directory resolved to: " + uploadBasePath);
    }

    /**
     * Multipart submission: JSON + files + tempId (main flow).
     * Always forwards tempId to the service so ONLINE_APPLICATION gets applicationNo + status='C'.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitApplicationMultipart(
            @RequestPart("formData") FormDataDto formData,
            @RequestPart(value = "idCopy", required = false) MultipartFile idCopy,
            @RequestPart(value = "ownershipCertificate", required = false) MultipartFile ownershipCertificate,
            @RequestPart(value = "gramaNiladhariCertificate", required = false) MultipartFile gramaNiladhariCertificate,
            @RequestPart(value = "threephChartedEngineerCertificate", required = false) MultipartFile threephChartedEngineerCertificate,
            @RequestPart("tempId") String tempId
    ) {
        try {
            if (!formData.getApplicationDto().getDeptId().matches("^\\d{3}\\.\\d{2}$")) {
                throw new IllegalArgumentException("Invalid Cost center number format");
            }

            // Persist and update ONLINE_APPLICATION via tempId
            String applicationNo = applicationWiringLDService.saveFullApplication(formData, tempId);

            // Save provided files (each call is a no-op if file is null/empty)
            saveFile(idCopy, tempId, "idCopy");
            saveFile(ownershipCertificate, tempId, "ownershipCertificate");
            saveFile(gramaNiladhariCertificate, tempId, "gramaNiladhariCertificate");
            saveFile(threephChartedEngineerCertificate, tempId, "threephChartedEngineerCertificate");

            return ResponseEntity.ok().body(Map.of(
                    "applicationNo", applicationNo,
                    "message", "Application submitted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private void saveFile(MultipartFile file, String tempId, String docType) throws IOException {
        if (file == null || file.isEmpty()) return;

        String original = file.getOriginalFilename();
        String extension = (original != null && original.contains(".")) ?
                original.substring(original.lastIndexOf('.')) : "";
        String newFilename = tempId + "_" + docType + extension;

        // Use Path.resolve to join safely across OSes (no manual separators)
        Path filePath = uploadBasePath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        DocumentUpload documentUpload = new DocumentUpload();
        documentUpload.setDocId(UUID.randomUUID().toString().substring(0, 10));
        documentUpload.setTempId(tempId);
        documentUpload.setDocpath(filePath.toAbsolutePath().toString());
        documentUpload.setUploadedDate(LocalDate.now());
        documentUploadRepository.save(documentUpload);
    }

    // ===== Utility endpoints you already had =====

    @GetMapping("/new-id")
    public ResponseEntity<String> getNewApplicationId(@RequestParam String deptId) {
        String newAppId = applicationWiringLDService.createNewApplicationId(deptId);
        return ResponseEntity.ok(newAppId);
    }

    @GetMapping("/all")
    public List<String> getAllApplicationNos() {
        return applicationService.getAllApplicationNos();
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateApplicationNo(@RequestParam String number) {
        boolean exists = applicationService.validateApplicationNo(number);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/save")
    public ResponseEntity<Application> saveApplication(@RequestBody ApplicationDTO applicationDTO, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(401).build();
        }
        Application savedApplication = applicationService.saveApplication(applicationDTO, username);
        return ResponseEntity.ok(savedApplication);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateApplication(
            @RequestParam String applicationId,
            @RequestBody ApplicationDTO applicationDTO,
            HttpSession session) {
        String sessionUsername = (String) session.getAttribute("username");
        if (sessionUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }
        try {
            Application updatedApplication = applicationService.updateApplication(applicationId, applicationDTO, sessionUsername);
            return ResponseEntity.ok(updatedApplication);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getApplicationById(@RequestParam String applicationId) {
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        return application.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ===== NEW ENDPOINT: Get 10 most recent applications =====
    // Change return type from ResponseEntity<List<Application>> to ResponseEntity<List<String>>
    @GetMapping("/recent")
    public ResponseEntity<List<String>> getRecentApplications() {
        List<String> recentApplications = applicationService.getRecentApplications(10);
        return ResponseEntity.ok(recentApplications);
    }

}
