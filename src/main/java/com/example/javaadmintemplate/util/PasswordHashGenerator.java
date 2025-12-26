package com.example.javaadmintemplate.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Raw password: " + rawPassword);
        System.out.println("Hashed password: " + hashedPassword);

        // Test the hash we're using
        String existingHash = "$2a$10$N9qo8uLOickgx2ZMRZoMye.Ji33FQ5hGJY5Mq6W7P8kBP4LZDCqGy";
        boolean matches = encoder.matches(rawPassword, existingHash);
        System.out.println("Does '" + rawPassword + "' match existing hash: " + matches);
    }
}
