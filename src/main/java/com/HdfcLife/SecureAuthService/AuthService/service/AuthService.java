package com.HdfcLife.SecureAuthService.AuthService.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Dev 2 will implement JWT logic later
    public String login(String username, String password) {
        // Temporary dummy response
        return "DUMMY_TOKEN_FOR_" + username;
    }

    public boolean authenticate(String token) {
        // Temporary dummy verification
        return token != null && token.startsWith("DUMMY_TOKEN_FOR_");
    }

    public void logout(String token) {
        // Temporary dummy logout
        System.out.println("Logout token: " + token);
    }
}
