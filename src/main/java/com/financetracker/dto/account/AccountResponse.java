package com.financetracker.dto.account;

import com.financetracker.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response containing account information")
public record AccountResponse(
    @Schema(description = "Unique account identifier")
    UUID id,

    @Schema(description = "Account name", example = "My Bank Account")
    String name,

    @Schema(description = "Account type", example = "BANK_ACCOUNT")
    AccountType accountType,

    @Schema(description = "Current account balance", example = "950.25")
    BigDecimal balance,

    @Schema(description = "Creation timestamp")
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp")
    LocalDateTime updatedAt
) {}