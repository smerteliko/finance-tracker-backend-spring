package com.financetracker.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Map;

@Schema(description = "A monthly financial summary with analytics and budget recommendations.")
public record MonthlySummaryResponse(
    @Schema(description = "The main financial analytics for the month.")
    AnalyticsResponse analytics,

    @Schema(description = "The month of the summary.", example = "JANUARY")
    Month month,

    @Schema(description = "The year of the summary.", example = "2024")
    int year,

    @Schema(description = "Budget recommendations based on past spending habits.", example = "{\"Groceries_recommended\": 256.95, \"Rent_recommended\": 1080.00}")
    Map<String, BigDecimal> budgetRecommendations
) {}
