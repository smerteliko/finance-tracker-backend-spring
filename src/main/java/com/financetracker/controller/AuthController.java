package com.financetracker.controller;

import com.financetracker.dto.login.LoginRequest;
import com.financetracker.entity.User;
import com.financetracker.services.Auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and authentication endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
        summary = "Register a new user",
        description = "Creates a new user account and returns a JWT token for authentication. " +
            "The token should be included in the Authorization header for protected endpoints.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User registration details",
            required = true,
            content = @Content(
                schema = @Schema(implementation = User.class),
                examples = @ExampleObject(value = """
                    {
                      "email": "john.doe@email.com",
                      "password": "password123",
                      "firstName": "John",
                      "lastName": "Doe"
                    }
                """)
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = @Content(schema = @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        String token = authService.register(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates user credentials and returns a JWT token. " +
            "Use this token in the Authorization header for all protected endpoints.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User login credentials",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequest.class),
                examples = @ExampleObject(value = """
                    {
                      "email": "john.doe@email.com",
                      "password": "password123"
                    }
                """)
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."))),
        @ApiResponse(responseCode = "400", description = "Invalid credentials format"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/token-info")
    @Operation(
        summary = "Get token information",
        description = "Returns information about JWT token requirements and expiration"
    )
    public ResponseEntity<String> getTokenInfo() {
        return ResponseEntity.ok("""
            JWT Token Information:
            - Token expiration: 24 hours
            - Header format: Authorization: Bearer <token>
            - Required for all endpoints except /api/auth/** and /api/health
            """);
    }
}