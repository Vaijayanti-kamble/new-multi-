package com.example.profiledocument.controller;

import com.example.profiledocument.entity.Employee;
import com.example.profiledocument.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee) {
        if (!employeeService.isValidEmail(employee.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }
        return ResponseEntity.ok("Valid email format");
    }
}
