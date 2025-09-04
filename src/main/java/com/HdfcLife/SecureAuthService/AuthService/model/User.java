package com.HdfcLife.SecureAuthService.AuthService.model; // Use your actual package name

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false) // Add this line for the role
    private String role;

    // --- Constructors, Getters, Setters ---

    public User() {}

    public User(String username, String password, String role) { // Add role here
        this.username = username;
        this.password = password;
        this.role = role; // Add this line
    }

    // Add Getters and Setters for the new 'role' field
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Other getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", role='" + role + '\'' + // Add role to toString
                '}';
    }

}