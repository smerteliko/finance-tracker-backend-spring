package com.financetracker.dto.account;

import com.financetracker.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Schema(description = "Request for creating or updating an account")
public record AccountRequest(
    @Schema(description = "Account name", example = "My Bank Account")
    @NotBlank(message = "Account name cannot be empty")
    String name,

    @Schema(description = "Account type", example = "BANK_ACCOUNT")
    @NotNull(message = "Account type cannot be empty")
    AccountType accountType,

    @Schema(description = "Initial balance (or current balance on update)", example = "1000.50")
    @DecimalMin(value = "0.00", inclusive = true, message = "Balance cannot be negative")
    @NotNull(message = "Initial balance cannot be empty")
    BigDecimal initialBalance
) {}