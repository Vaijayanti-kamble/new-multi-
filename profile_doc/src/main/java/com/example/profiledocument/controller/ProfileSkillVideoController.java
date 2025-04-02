package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileSkillVideo;
import com.example.profiledocument.service.ProfileSkillVideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/profile-skill-video")
public class ProfileSkillVideoController {

    private final ProfileSkillVideoService service;
    private static final String ERROR_MESSAGE = "Error: ";

    public ProfileSkillVideoController(ProfileSkillVideoService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String id = service.addVideo(file);
            return ResponseEntity.ok("Video uploaded successfully, ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateVideo(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            String response = service.updateVideo(id, file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileSkillVideo> getVideo(@PathVariable String id) {
        try {
            ProfileSkillVideo video = service.getVideo(id);
            return ResponseEntity.ok(video);
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable String id) {
        try {
            String response = service.deleteVideo(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
}
