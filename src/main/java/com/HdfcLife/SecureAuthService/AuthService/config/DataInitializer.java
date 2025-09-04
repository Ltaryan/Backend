package com.HdfcLife.SecureAuthService.AuthService.config;

import com.HdfcLife.SecureAuthService.AuthService.model.User;
import com.HdfcLife.SecureAuthService.AuthService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // We check if the table is empty. If so, we create our default users.
        if (userRepository.count() == 0) {
            logger.info("Database is empty. Seeding initial users...");

            // Create admin user with an ENCODED password
            User admin = new User("admin", passwordEncoder.encode("password123"), "ADMIN");
            userRepository.save(admin);
            logger.info("Created user: 'admin' with role 'ROLE_ADMIN'");

            // Create regular user with an ENCODED password
            User user = new User("user", passwordEncoder.encode("userpass"), "ROLE_USER");
            userRepository.save(user);
            logger.info("Created user: 'user' with role 'ROLE_USER'");

            logger.info("Finished seeding initial users.");
        } else {
            logger.info("Database already contains users. Skipping data seeding.");
        }
    }
}