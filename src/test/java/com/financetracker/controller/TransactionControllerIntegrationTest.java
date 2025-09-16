package com.financetracker.controller;

import com.financetracker.dto.transaction.TransactionRequest;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.entity.Category;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.repository.CategoryRepository;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.repository.UserRepository;
import com.financetracker.services.JWT.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TransactionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private String jwtToken;
    private User testUser;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User();
        testUser.setEmail("john.doe@email.com");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        userRepository.save(testUser);

        testCategory = new Category();
        testCategory.setName("Food");
        testCategory.setType(Category.CategoryType.EXPENSE);
        categoryRepository.save(testCategory);

        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType(com.financetracker.entity.Transaction.TransactionType.EXPENSE);
        transaction.setUser(testUser);
        transaction.setCategory(testCategory);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(testUser.getEmail(), testUser.getPassword(), List.of());
        jwtToken = jwtService.generateToken(userDetails);
    }

    @Test
    void getUserTransactions_ShouldReturnTransactions_WhenAuthenticated() {

        ResponseEntity<List<TransactionResponse>> response = restTemplate.exchange(
            "/api/transactions/", HttpMethod.GET, new HttpEntity<>(createHeaders(jwtToken)),
            new ParameterizedTypeReference<List<TransactionResponse>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }
}