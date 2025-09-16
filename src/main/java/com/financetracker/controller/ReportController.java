package com.financetracker.controller;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.services.Reports.ReportService;
import com.financetracker.services.UserServices.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Financial reports generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/csv")
    @Operation(summary = "Generate CSV report", description = "Generates a CSV report of transactions for the specified period")
    public ResponseEntity<String> generateCSVReport(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody AnalyticsRequest request) {

        String csv = reportService.generateCSVReport(userDetails.getUserId(), request.getStartDate(), request.getEndDate());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions-report.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csv);
    }

    @PostMapping("/summary")
    @Operation(summary = "Generate text summary", description = "Generates a text summary of transactions for the specified period")
    public ResponseEntity<String> generateTextSummary(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody AnalyticsRequest request) {

        String summary = reportService.generateTextSummary(userDetails.getUserId(), request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(summary);
    }
}
