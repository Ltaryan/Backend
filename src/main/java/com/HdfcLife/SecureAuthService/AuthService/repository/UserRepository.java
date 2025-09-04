package com.HdfcLife.SecureAuthService.AuthService.repository; // Use your actual package name

import com.HdfcLife.SecureAuthService.AuthService.model.User; // Use your actual package name
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA will automatically generate the query for this method
    // based on its name. It will be equivalent to:
    // SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}