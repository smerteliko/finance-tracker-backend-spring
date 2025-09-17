package com.financetracker.dto.login;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "User login credentials")
public class LoginRequest {

    @NotBlank
    @Schema(
        description = "User's email address",
        example = "john.doe@email.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @NotBlank
    @Schema(
        description = "User's password",
        example = "password123",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 6
    )
    private String password;
}
