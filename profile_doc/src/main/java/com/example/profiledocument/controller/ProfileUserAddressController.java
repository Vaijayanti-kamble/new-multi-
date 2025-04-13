package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileAddress;
import com.example.profiledocument.service.ProfileUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/profile-user-address")
public class ProfileUserAddressController {

    @Autowired
    private ProfileUserAddressService service;

    @PostMapping("/save-user-address")
    public ResponseEntity<String> save(@RequestBody ProfileAddress address) {
        try {
            return ResponseEntity.ok(service.saveOrUpdateAddress(address));
        } catch (ExecutionException | InterruptedException | IllegalArgumentException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/get-user-address/{profileUserId}")
    public ResponseEntity<?> get(@PathVariable String profileUserId) {
        try {
            ProfileAddress address = service.getAddress(profileUserId);
            if (address == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(address);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-user-address/{profileUserId}")
    public ResponseEntity<String> delete(@PathVariable String profileUserId) {
        try {
            return ResponseEntity.ok(service.deleteAddress(profileUserId));
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
