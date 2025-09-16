package com.financetracker.dto;

import com.financetracker.entity.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Transaction response with complete details")
public class TransactionResponse {

    @Schema(description = "Unique transaction ID", example = "123")
    private Long id;

    @Schema(description = "Public unique identifier", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private String uuid;

    @Schema(description = "Transaction amount", example = "150.75")
    private BigDecimal amount;

    @Schema(description = "Transaction description", example = "Weekly groceries shopping")
    private String description;

    @Schema(description = "Transaction type", example = "EXPENSE", allowableValues = {"INCOME", "EXPENSE"})
    private Transaction.TransactionType type;

    @Schema(description = "Transaction date and time", example = "2024-01-15T10:30:00")
    private LocalDateTime date;

    @Schema(description = "Transaction creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Transaction last update timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User ID who owns this transaction", example = "1")
    private Long userId;

    @Schema(description = "Category ID", example = "4")
    private Long categoryId;

    @Schema(description = "Category name", example = "Groceries")
    private String categoryName;

    @Schema(description = "Category color in HEX format", example = "#FF5722")
    private String categoryColor;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.uuid = transaction.getUuid();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.date = transaction.getDate();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
        this.userId = transaction.getUser().getId();
        this.categoryId = transaction.getCategory().getId();
    }
}