package com.financetracker.controller;

import com.financetracker.dto.login.LoginRequest;
import com.financetracker.entity.User;
import com.financetracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void registerUser_ShouldReturnJwtToken_WhenSuccessful() {
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
        user.setFirstName("Test");
        user.setLastName("User");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/auth/register", user, String.class);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length() > 10);
        assertTrue(userRepository.findByEmail("testuser@example.com").isPresent());
    }

    @Test
    void loginUser_ShouldReturnJwtToken_WhenSuccessful() {
        User user = new User();
        user.setEmail("loginuser@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setFirstName("Login");
        user.setLastName("User");
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("loginuser@example.com");
        loginRequest.setPassword("password123");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/auth/login", loginRequest, String.class);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length() > 10);
    }

}