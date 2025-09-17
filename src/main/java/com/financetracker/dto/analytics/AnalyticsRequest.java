package com.financetracker.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Analytics period request")
public class AnalyticsRequest {

    @NotNull
    @Schema(description = "Start date of the period", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;

    @NotNull
    @Schema(description = "End date of the period", example = "2024-01-31T23:59:59")
    private LocalDateTime endDate;
}
