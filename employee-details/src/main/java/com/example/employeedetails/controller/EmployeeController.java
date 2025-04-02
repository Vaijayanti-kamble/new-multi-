package com.example.employeedetails.controller;

import com.example.employeedetails.model.Employee;
import com.example.employeedetails.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees-details")
public class EmployeeController {
    private static final String ERROR_MESSAGE = "Error: ";

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<String> saveUserProfessionDetails(@RequestBody Employee details) {
        try {
            String id = employeeService.saveEmployeeDetails(details);
            return ResponseEntity.ok("Saved with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserProfessionDetailsById(@PathVariable String id) {
        try {
            Employee details = employeeService.getEmployeeDetailsById(id);
            if (details != null) {
                return ResponseEntity.ok(details);
            } else {
                return ResponseEntity.status(404).body("No document found with ID: " + id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Re-interrupt the thread
            return ResponseEntity.status(500).body(ERROR_MESSAGE + "Operation was interrupted.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ERROR_MESSAGE + e.getMessage());
        }
    }
}
