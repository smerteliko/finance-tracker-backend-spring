package com.financetracker.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Request for fetching analytics data within a date range")
public record AnalyticsRequest(
    @Schema(description = "Start date (inclusive)", example = "2023-01-01")
    @NotNull(message = "Start date cannot be empty")
    LocalDate startDate,

    @Schema(description = "End date (inclusive)", example = "2023-12-31")
    @NotNull(message = "End date cannot be empty")
    LocalDate endDate
) {}