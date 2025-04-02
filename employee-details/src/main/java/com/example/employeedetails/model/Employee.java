package com.example.employeedetails.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private String id;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Phone number is required")
    private String phone;

    @NotEmpty(message = "Email ID is required")
    private String email;

    @NotEmpty(message = "Role ID is required")
    private String role;

    public void setid(String id) {
        this.id = id;
    }
}
