package com.financetracker.dto.analytics;

import com.financetracker.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Aggregated amount for a specific category")
public record CategorySummary(
    @Schema(description = "Category ID")
    UUID categoryId,

    @Schema(description = "Category name", example = "Salary")
    String categoryName,

    @Schema(description = "Category type", example = "INCOME")
    CategoryType type,

    @Schema(description = "Total aggregated amount for this category", example = "5000.00")
    BigDecimal totalAmount
) {}