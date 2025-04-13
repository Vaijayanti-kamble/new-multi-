package com.example.profiledocument.controller;

import com.example.profiledocument.entity.ProfileUserEmail;
import com.example.profiledocument.service.ProfileUserEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user-email")
@Validated
public class ProfileUserEmailController {

    private final ProfileUserEmailService employeeService;

    public ProfileUserEmailController(ProfileUserEmailService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add-user-email")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody ProfileUserEmail employee) {
        if (!employeeService.isValidEmail(employee.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }
        return ResponseEntity.ok("Valid email format");
    }
}
