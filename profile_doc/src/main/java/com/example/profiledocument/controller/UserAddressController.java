package com.example.profiledocument.controller;

import com.example.profiledocument.entity.UserAddress;
import com.example.profiledocument.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user-address")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    // ✅ Add a new address
    @PostMapping("/add")
    public ResponseEntity<String> addAddress(@RequestBody UserAddress address) {
        try {
            String response = userAddressService.addAddress(address);
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Error adding address: " + e.getMessage());
        }
    }

    // ✅ Update an existing address
    @PutMapping("/update/{addressId}")
    public ResponseEntity<String> updateAddress(@PathVariable String addressId, @RequestBody UserAddress address) {
        try {
            String response = userAddressService.updateAddress(addressId, address);
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Error updating address: " + e.getMessage());
        }
    }

    // ✅ Get address by ID
    @GetMapping("/get/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable String addressId) {
        try {
            UserAddress address = userAddressService.getAddressById(addressId);
            if (address == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(address);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Error retrieving address: " + e.getMessage());
        }
    }

    // ✅ Delete an address
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
        try {
            String response = userAddressService.deleteAddress(addressId);
            return ResponseEntity.ok(response);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.internalServerError().body("Error deleting address: " + e.getMessage());
        }
    }
}

