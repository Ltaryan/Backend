package com.HdfcLife.SecureAuthService.AuthService.service;


import com.HdfcLife.SecureAuthService.AuthService.model.User;
import com.HdfcLife.SecureAuthService.AuthService.repository.UserRepository;
import com.HdfcLife.SecureAuthService.AuthService.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final InMemoryTokenStore tokenStore;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(@Lazy AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, InMemoryTokenStore tokenStore, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenStore = tokenStore;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        logger.info("Authenticating user: {}", username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtUtil.generateToken(userDetails);
        tokenStore.storeToken(token);
        logger.info("Token generated successfully for user: {}", username);
        return token;
    }

    public String validateTokenAndGetUserInfo(String token) {
        try {
            if (!tokenStore.isTokenValid(token)) {
                logger.warn("Validation failed: Token not found in active store.");
                return null;
            }

            String username = jwtUtil.extractUsername(token);

            // --- THIS IS THE CORRECTED LINE (around line 44) ---
            // The variable 'userDetails' must be of type UserDetails.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                return username;
            } else {
                logger.warn("Validation failed: JWT validation logic failed for user {}", username);
                return null;
            }
        } catch (Exception e) {
            logger.error("Token validation threw an exception: {}", e.getMessage());
            return null;
        }
    }

    public boolean logout(String token) {
        if (tokenStore.isTokenValid(token)) {
            tokenStore.invalidateToken(token);
            logger.info("Token invalidated successfully.");
            return true;
        }
        logger.warn("Logout attempt for a token that was not in the store.");
        return false;
    }

    public void signup(String username, String password) {

        logger.info("User {} signed up successfully.", username);

        User admin = new User(username, passwordEncoder.encode(password), "ADMIN");
        userRepository.save(admin);
    }
}