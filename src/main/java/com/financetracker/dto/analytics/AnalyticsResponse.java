package com.financetracker.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Comprehensive financial analytics for a specified period.")
public record AnalyticsResponse(
    @Schema(description = "Total income for the period.", example = "3500.00")
    BigDecimal totalIncome,

    @Schema(description = "Total expenses for the period.", example = "1555.50")
    BigDecimal totalExpense,

    @Schema(description = "Financial balance (total income - total expense).", example = "1944.50")
    BigDecimal balance,

    @Schema(description = "Breakdown of expenses by category.", example = "{\"Groceries\": 285.50, \"Rent\": 1200.00}")
    Map<String, BigDecimal> expensesByCategory,

    @Schema(description = "Breakdown of income by category.", example = "{\"Salary\": 3500.00}")
    Map<String, BigDecimal> incomeByCategory,

    @Schema(description = "Total number of transactions in the period.", example = "15")
    int transactionCount,

    @Schema(description = "Start date and time of the analysis period.", example = "2024-01-01T00:00:00")
    LocalDateTime periodStart,

    @Schema(description = "End date and time of the analysis period.", example = "2024-01-31T23:59:59")
    LocalDateTime periodEnd
) {}
