package com.mgt.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgt.serviceimpl.EmailService;
import com.mgt.serviceimpl.OtpService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import com.mgt.repository.UserRepo;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepo userRepository; // Add this

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {
        System.out.println("send otp function is called  " + email);

        // Optional: Check if email exists before sending OTP
        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Email not found"));
        }

        String otp = otpService.generatedOtp(email);
        emailService.sendAuthEmail(email, "Your Login OTP", "Use this OTP to log in your email: " + otp);
        return ResponseEntity.ok(Collections.singletonMap("message", "OTP sent to email"));
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        email = email.toLowerCase(); // normalize to avoid mismatch

        if (!userRepository.existsByEmail(email)) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "Email not found"));
        }

        boolean isValid = otpService.verifyOtp(email, otp);

        if (isValid) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid or expired OTP"));
        }
    }

}
