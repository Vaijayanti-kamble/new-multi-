package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileUserDetails;
import com.example.profiledocument.service.ProfileUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile-user-details")
public class ProfileUserDetailsController {

    private static final String ERROR_MESSAGE = "Error: ";
    private static final String INTERRUPTED_MESSAGE = "Operation was interrupted.";
    private final ProfileUserDetailsService service;

    public ProfileUserDetailsController(ProfileUserDetailsService service) {
        this.service = service;
    }

    @PostMapping("/save-user-details")
    public ResponseEntity<String> saveUserProfessionDetails(@RequestBody ProfileUserDetails details) {
        try {
            String id = service.saveUserProfessionDetails(details);
            return ResponseEntity.ok("Saved with userDetailsId: " + id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(ERROR_MESSAGE + INTERRUPTED_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @GetMapping("/get-user-details/{id}")
    public ResponseEntity<Object> getUserProfessionDetailsById(@PathVariable String id) {
        try {
            ProfileUserDetails details = service.getUserProfessionDetailsById(id);
            if (details != null) {
                return ResponseEntity.ok(details);
            } else {
                return ResponseEntity.status(404).body("No document found with ID: " + id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(ERROR_MESSAGE + INTERRUPTED_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }

    @DeleteMapping("/delete-user-details/{id}")
    public ResponseEntity<String> deleteUserProfessionDetails(@PathVariable String id) {
        try {
            String result = service.deleteUserProfessionDetails(id);
            return ResponseEntity.ok(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(ERROR_MESSAGE + INTERRUPTED_MESSAGE);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
}
