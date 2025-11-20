package com.financetracker.controller;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.dto.analytics.AnalyticsResponse;
import com.financetracker.dto.analytics.MonthlySummaryResponse;
import com.financetracker.services.Analytics.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; // FIX: Изменено на GetMapping
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Financial reporting and aggregation")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(summary = "Returns the user's current overall balance (all-time)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the current overall balance", content = @Content(schema = @Schema(implementation = Map.class, example = "{\"currentBalance\": 520.45}"))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/balance")
    public ResponseEntity<Map<String, BigDecimal>> getCurrentBalance() {
        BigDecimal balance = analyticsService.getCurrentBalance();
        return ResponseEntity.ok(Map.of("currentBalance", balance));
    }

    @Operation(summary = "Returns detailed financial analytics (income, expense, breakdown) for a given period. Defaults to the last 30 days if no dates are provided.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns detailed financial analytics", content = @Content(schema = @Schema(implementation = AnalyticsResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid date range or request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/")
    public ResponseEntity<AnalyticsResponse> getAnalyticsForPeriod(
        AnalyticsRequest request) {

        AnalyticsResponse response = analyticsService.getAnalyticsForPeriod(request);
        return ResponseEntity.ok(response);
    }

}