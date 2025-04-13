package com.example.profiledocument.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private ProfileUserEmailService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new ProfileUserEmailService();
    }

    // 🔹 Test Valid Email
    @Test
    void testIsValidEmail_Valid() {
        assertTrue(employeeService.isValidEmail("test@example.com"));
        assertTrue(employeeService.isValidEmail("user.name@domain.co"));
        assertTrue(employeeService.isValidEmail("valid_email123@company.org"));
    }

    // 🔹 Test Invalid Email (Missing @ symbol)
    @Test
    void testIsValidEmail_Invalid_MissingAtSymbol() {
        assertFalse(employeeService.isValidEmail("invalidemail.com"));
    }

    // 🔹 Test Invalid Email (Missing domain)
    @Test
    void testIsValidEmail_Invalid_MissingDomain() {
        assertFalse(employeeService.isValidEmail("user@"));
    }

    // 🔹 Test Invalid Email (No extension)
    @Test
    void testIsValidEmail_Invalid_NoExtension() {
        assertFalse(employeeService.isValidEmail("user@domain"));
    }

    // 🔹 Test Invalid Email (Special Characters)
    @Test
    void testIsValidEmail_Invalid_SpecialCharacters() {
        assertFalse(employeeService.isValidEmail("user@domain@com"));

    }

    // 🔹 Test Invalid Email (Null)
    @Test
    void testIsValidEmail_Invalid_Null() {
        assertFalse(employeeService.isValidEmail(null));
    }

    // 🔹 Test Invalid Email (Empty String)
    @Test
    void testIsValidEmail_Invalid_EmptyString() {
        assertFalse(employeeService.isValidEmail(""));
    }
}
