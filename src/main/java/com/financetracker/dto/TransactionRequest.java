package com.financetracker.dto;

import com.financetracker.entity.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Transaction creation and update request")
public class TransactionRequest {

    @NotNull
    @Positive
    @Schema(
        description = "Transaction amount. Positive number for both income and expense",
        example = "150.75",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minimum = "0.01"
    )
    private BigDecimal amount;

    @Schema(
        description = "Transaction description",
        example = "Weekly groceries shopping at supermarket",
        maxLength = 500
    )
    private String description;

    @NotNull
    @Schema(
        description = "Transaction type: INCOME or EXPENSE",
        example = "EXPENSE",
        requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = {"INCOME", "EXPENSE"}
    )
    private Transaction.TransactionType type;

    @NotNull
    @Schema(
        description = "Transaction date and time in ISO format",
        example = "2024-01-15T10:30:00",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime date;

    @NotNull
    @Schema(
        description = "User ID who owns this transaction",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long userId;

    @NotNull
    @Schema(
        description = "Category ID for this transaction",
        example = "4",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long categoryId;
}