package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileUserSkill;
import com.example.profiledocument.service.ProfileUserSkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/profile-user-skills")
public class ProfileUserSkillController {

    private final ProfileUserSkillService profileUserSkillService;

    public ProfileUserSkillController(ProfileUserSkillService profileUserSkillService) {
        this.profileUserSkillService = profileUserSkillService;
    }

    // 🔹 Create a new ProfileUserSkill
    @PostMapping("/save-user-skills")
    public ResponseEntity<String> saveProfileUserSkill(@RequestBody ProfileUserSkill profileSkill) {
        try {
            String id = profileUserSkillService.saveProfileUserSkill(profileSkill);
            return ResponseEntity.ok("UserSkillID: " + id);
        } catch (ExecutionException e) {
            return ResponseEntity.internalServerError().body("Error saving ProfileUserSkill.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 🔹 Re-interrupt the thread
            return ResponseEntity.internalServerError().body("Error saving ProfileUserSkill due to interruption.");
        }
    }

    // 🔹 Get ProfileUserSkill by ID
    @GetMapping("/get-user-skills/{id}")
    public ResponseEntity<ProfileUserSkill> getProfileUserSkillById(@PathVariable String id) {
        try {
            ProfileUserSkill profileSkill = profileUserSkillService.getProfileUserSkillById(id);
            if (profileSkill != null) {
                return ResponseEntity.ok(profileSkill);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ExecutionException e) {
            return ResponseEntity.internalServerError().build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔹 Update
    @PutMapping("/update-user-skills/{id}")
    public ResponseEntity<String> updateProfileUserSkill(@PathVariable String id, @RequestBody List<String> skills) {
        try {
            String message = profileUserSkillService.updateProfileUserSkill(id, skills);
            return ResponseEntity.ok(message);
        } catch (ExecutionException e) {
            return ResponseEntity.internalServerError().body("Error updating ProfileUserSkill.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().body("Error updating ProfileUserSkill due to interruption.");
        }
    }

    // 🔹 Delete
    @DeleteMapping("/delete-user-skills/{id}")
    public ResponseEntity<String> deleteProfileUserSkill(@PathVariable String id) {
        try {
            String message = profileUserSkillService.deleteProfileUserSkill(id);
            return ResponseEntity.ok(message);
        } catch (ExecutionException e) {
            return ResponseEntity.internalServerError().body("Error deleting ProfileUserSkill.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().body("Error deleting ProfileUserSkill due to interruption.");
        }
    }
}
