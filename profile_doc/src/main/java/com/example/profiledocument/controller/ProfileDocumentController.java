package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileDocument;
import com.example.profiledocument.service.ProfileDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/profile-doc")
public class ProfileDocumentController {

    private final ProfileDocumentService service;
    private static final String ERROR_MESSAGE = "Error: ";

    public ProfileDocumentController(ProfileDocumentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String id = service.addProfessionalDocument(file);
            return ResponseEntity.ok("File uploaded successfully, ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateFile(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            String response = service.updateProfessionalDocument(id, file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDocument> getDocument(@PathVariable String id) {
        try {
            ProfileDocument document = service.getProfessionalDocument(id);
            return ResponseEntity.ok(document);
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(null);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 🔹 Re-interrupt the thread
            return ResponseEntity.status(500).body(null);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable String id) {
        try {
            String response = service.deleteProfessionalDocument(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
}
