package com.financetracker.controller;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.dto.analytics.AnalyticsResponse;
import com.financetracker.services.Analytics.AnalyticsService;
import com.financetracker.services.UserServices.CustomUserDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/")
    @Operation(
        summary = "Get financial analytics for user",
        description = "Returns comprehensive financial analytics including income, expenses, balance and category breakdown for the specified period",
        responses = {
            @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully",
                content = @Content(schema = @Schema(implementation = AnalyticsResponse.class),
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
    public ResponseEntity<AnalyticsResponse> getUserAnalytics(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody AnalyticsRequest request) {
        return ResponseEntity.ok(analyticsService.getUserAnalytics(userDetails.getUserId(), request.getStartDate(), request.getEndDate()));
    }

    @GetMapping("/monthly/{year}/{month}")
    @Operation(
        summary = "Get monthly financial summary",
        description = "Returns monthly financial summary with analytics and budget recommendations",
        responses = {
            @ApiResponse(responseCode = "200", description = "Monthly summary retrieved successfully",
                content = @Content(schema = @Schema(implementation = AnalyticsService.MonthlySummaryResponse.class)))
        }
    )
    public ResponseEntity<AnalyticsService.MonthlySummaryResponse> getMonthlySummary(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
        @Parameter(description = "Year", example = "2024") @PathVariable int year,
        @Parameter(description = "Month", example = "JANUARY") @PathVariable Month month) {
        return ResponseEntity.ok(analyticsService.getMonthlySummary(userDetails.getUserId(), year, month));
    }

    @GetMapping("/balance")
    @Operation(
        summary = "Get current balance",
        description = "Returns the current balance for the user (all time)"
    )
    public ResponseEntity<BigDecimal> getCurrentBalance(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        BigDecimal balance = analyticsService.getCurrentBalance(userDetails.getUserId());
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/period-balance")
    @Operation(summary = "Get balance for specific period for current user")
    public ResponseEntity<BigDecimal> getBalanceForPeriod(
        @RequestBody AnalyticsRequest request,
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(analyticsService.getBalanceForPeriod(userDetails.getUserId(), request.getStartDate(), request.getEndDate()));
    }
}