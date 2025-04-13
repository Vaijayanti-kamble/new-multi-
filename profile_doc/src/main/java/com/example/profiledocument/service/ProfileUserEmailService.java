package com.example.profiledocument.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ProfileUserEmailService {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        return email != null && pattern.matcher(email).matches();
    }
}
