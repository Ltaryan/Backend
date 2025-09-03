package com.HdfcLife.SecureAuthService.AuthService.controller;

import com.HdfcLife.SecureAuthService.AuthService.model.LoginRequest;
import com.HdfcLife.SecureAuthService.AuthService.service.AuthService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @GetMapping("/verify")
    public String verify(@RequestHeader("Authorization") String token) {
        boolean valid = authService.authenticate(token.replace("Bearer ", ""));
        return valid ? "Token is valid" : "Token is invalid";
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return "Logged out successfully";
    }
}
