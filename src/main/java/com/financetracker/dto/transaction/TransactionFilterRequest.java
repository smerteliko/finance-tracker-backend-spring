package com.financetracker.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(description = "Transaction filter and pagination request")
public class TransactionFilterRequest {

    @Schema(description = "Page number (0-based)", example = "0")
    private int page = 0;

    @Schema(description = "Page size", example = "10")
    private int size = 10;

    @Schema(description = "Start date for filtering (ISO format)", example = "2024-01-01T00:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Schema(description = "End date for filtering (ISO format)", example = "2024-01-31T23:59:59")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Schema(description = "Transaction type filter", example = "EXPENSE")
    private String type;

    @Schema(description = "Category ID filter", example = "4")
    private Long categoryId;
}
