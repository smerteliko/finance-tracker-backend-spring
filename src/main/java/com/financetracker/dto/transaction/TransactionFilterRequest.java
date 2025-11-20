package com.financetracker.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Transaction filter and pagination request")
public record TransactionFilterRequest (

    @Schema(description = "Page number (0-based)", example = "0")
    @Min(value = 1, message = "Page number must be positive")
    int page,

    @Schema(description = "Page size", example = "10")
    @Min(value = 1, message = "Limit must be positive")
    @Max(value = 100, message = "Limit cannot exceed 100")
    int limit,

    @Schema(description = "Field to sort by", example = "transactionDate")
    @Pattern(regexp = "date|amount|createdAt", message = "Invalid sort field. Must be one of: date, amount, createdAt")
    String sortBy,

    @Schema(description = "Sort direction", example = "DESC")
    @Pattern(regexp = "ASC|DESC", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Sort order must be ASC or DESC")
    String sortOrder,

    @Schema(description = "Start date for filtering (ISO format)", example = "2024-01-01T00:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime startDate,

    @Schema(description = "End date for filtering (ISO format)", example = "2024-01-31T23:59:59")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime endDate,

    @Schema(description = "Transaction type filter", example = "EXPENSE")
    String type,

    @Schema(description = "Category ID filter", example = "4")
    UUID categoryId,

    @Schema(description = "Filter by Account ID", example = "a2b4c6d8-9e10-11f2-b345-123456789012")
    UUID accountId
){
    public TransactionFilterRequest {
        if (page == 0) page = 1;
        if (limit == 0) limit = 10;
        if (sortBy == null) sortBy = "date";
        if (sortOrder == null) sortOrder = "DESC";
    }
}
