package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileResumeDocument;
import com.example.profiledocument.service.ProfileResumeDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/profile-resume-document")
public class ProfileResumeDocumentController {

    private final ProfileResumeDocumentService service;
    private static final String ERROR_MESSAGE = "Error: ";

    public ProfileResumeDocumentController(ProfileResumeDocumentService service) {
        this.service = service;
    }

    @PostMapping("/save-profile-resume-document")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String id = service.addProfessionalDocument(file);
            return ResponseEntity.ok("File uploaded successfully, resumeDocumentId: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/update-profile-resume-document/{resumeDocumentId}")
    public ResponseEntity<String> updateFile(@PathVariable String resumeDocumentId, @RequestParam("file") MultipartFile file) {
        try {
            String response = service.updateProfessionalDocument(resumeDocumentId, file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @GetMapping("/get-profile-resume-document/{resumeDocumentId}")
    public ResponseEntity<ProfileResumeDocument> getDocument(@PathVariable String resumeDocumentId) {
        try {
            ProfileResumeDocument document = service.getProfessionalDocument(resumeDocumentId);
            return ResponseEntity.ok(document);
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(null);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/delete-profile-resume-document/{resumeDocumentId}")
    public ResponseEntity<String> deleteFile(@PathVariable String resumeDocumentId) {
        try {
            String response = service.deleteProfessionalDocument(resumeDocumentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
}
