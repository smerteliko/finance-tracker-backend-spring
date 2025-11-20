package com.financetracker.dto.transaction;

import com.financetracker.entity.Transaction;
import com.financetracker.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Schema(description = "Transaction creation and update request")
public record TransactionRequest (

    @NotNull
    @Positive
    @Schema(
        description = "Transaction amount. Positive number for both income and expense",
        example = "150.75",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minimum = "0.01"
    )
     BigDecimal amount,

    @Schema(
        description = "Transaction description",
        example = "Weekly groceries shopping at supermarket",
        maxLength = 500
    )
     String description,

    @NotNull
    @Schema(
        description = "Transaction type: INCOME or EXPENSE",
        example = "EXPENSE",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"INCOME", "EXPENSE"}
    )
    TransactionType type,

    @NotNull
    @Schema(
        description = "Transaction date and time in ISO format",
        example = "2024-01-15T10:30:00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
     LocalDateTime date,

    @NotNull
    @Schema(
        description = "Category ID for this transaction",
        example = "4",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    UUID categoryId,

    @Schema(description = "Unique account identifier", example = "a2b4c6d8-9e10-11f2-b345-123456789012")
    @NotNull(message = "Account ID cannot be empty")
    UUID accountId
){}