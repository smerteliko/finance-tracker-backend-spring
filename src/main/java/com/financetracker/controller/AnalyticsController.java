package com.financetracker.controller;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.services.Analytics.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Month;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Financial analytics and reporting endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping("/user/{userId}")
    @Operation(
        summary = "Get financial analytics for user",
        description = "Returns comprehensive financial analytics including income, expenses, balance and category breakdown for the specified period",
        responses = {
            @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully",
                content = @Content(schema = @Schema(implementation = AnalyticsService.AnalyticsResponse.class),
                    examples = @ExampleObject(value = """
                            {
                              "totalIncome": 3500.00,
                              "totalExpense": 1555.50,
                              "balance": 1944.50,
                              "expensesByCategory": {
                                "Groceries": 285.50,
                                "Rent": 1200.00,
                                "Utilities": 150.00
                              },
                              "incomeByCategory": {
                                "Salary": 3500.00
                              },
                              "transactionCount": 15,
                              "periodStart": "2024-01-01T00:00:00",
                              "periodEnd": "2024-01-31T23:59:59"
                            }
                        """)
                )
            )
        })
    public ResponseEntity<AnalyticsService.AnalyticsResponse> getUserAnalytics(
        @Parameter(description = "User ID", example = "1") @PathVariable Long userId,
        @RequestBody AnalyticsRequest request) {
        return ResponseEntity.ok(analyticsService.getUserAnalytics(userId, request.getStartDate(), request.getEndDate()));
    }

    @GetMapping("/user/{userId}/monthly/{year}/{month}")
    @Operation(
        summary = "Get monthly financial summary",
        description = "Returns monthly financial summary with analytics and budget recommendations",
        responses = {
            @ApiResponse(responseCode = "200", description = "Monthly summary retrieved successfully",
                content = @Content(schema = @Schema(implementation = AnalyticsService.MonthlySummaryResponse.class)))
        }
    )
    public ResponseEntity<AnalyticsService.MonthlySummaryResponse> getMonthlySummary(
        @Parameter(description = "User ID", example = "1") @PathVariable Long userId,
        @Parameter(description = "Year", example = "2024") @PathVariable int year,
        @Parameter(description = "Month", example = "JANUARY") @PathVariable Month month) {
        return ResponseEntity.ok(analyticsService.getMonthlySummary(userId, year, month));
    }

    @GetMapping("/user/{userId}/balance")
    @Operation(
        summary = "Get current balance",
        description = "Returns the current balance for the user (all time)"
    )
    public ResponseEntity<BigDecimal> getCurrentBalance(
        @Parameter(description = "User ID", example = "1") @PathVariable Long userId) {
        // Implementation would calculate balance from all transactions
        return ResponseEntity.ok(BigDecimal.valueOf(1944.50));
    }
}