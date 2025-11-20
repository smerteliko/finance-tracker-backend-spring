package com.financetracker.dto.transaction;

import com.financetracker.entity.Transaction;
import com.financetracker.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Transaction response with complete details")
public class TransactionResponse {

    @Schema(description = "Public unique identifier", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "Transaction amount", example = "150.75")
    private BigDecimal amount;

    @Schema(description = "Transaction description", example = "Weekly groceries shopping")
    private String description;

    @Schema(description = "Transaction type", example = "EXPENSE", allowableValues = {"INCOME", "EXPENSE"})
    private TransactionType type;

    @Schema(description = "Transaction date and time", example = "2024-01-15T10:30:00")
    private LocalDateTime date;

    @Schema(description = "Category ID", example = "4")
    private UUID categoryId;

    @Schema(description = "Account ID", example = "4")
    private UUID accountId;

    @Schema(description = "Transaction creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Transaction last update timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;


    public TransactionResponse(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.date = transaction.getDate();
        this.categoryId = transaction.getCategory().getId();
        this.accountId = transaction.getAccount().getId();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
    }
}