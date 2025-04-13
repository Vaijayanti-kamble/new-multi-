package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileResumeVideo;
import com.example.profiledocument.service.ProfileResumeVideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/profile-resume-video")
public class ProfileResumeVideoController {

    private final ProfileResumeVideoService service;
    private static final String ERROR_MESSAGE = "Error: ";

    public ProfileResumeVideoController(ProfileResumeVideoService service) {
        this.service = service;
    }

    @PostMapping("/save-profile-resume-video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            String id = service.addVideo(file);
            return ResponseEntity.ok("Video uploaded successfully, resumeVideoId: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/update-profile-resume-video/{id}")
    public ResponseEntity<String> updateVideo(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            String response = service.updateVideo(id, file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @GetMapping("/get-profile-resume-video/{id}")
    public ResponseEntity<ProfileResumeVideo> getVideo(@PathVariable String id) {
        try {
            ProfileResumeVideo video = service.getVideo(id);
            return ResponseEntity.ok(video);
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/delete-profile-resume-video/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable String id) {
        try {
            String response = service.deleteVideo(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
}
