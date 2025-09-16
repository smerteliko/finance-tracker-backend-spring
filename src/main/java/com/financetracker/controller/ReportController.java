package com.financetracker.controller;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.services.Reports.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Financial reports generation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/user/{userId}/csv")
    @Operation(summary = "Generate CSV report", description = "Generates a CSV report of transactions for the specified period")
    public ResponseEntity<String> generateCSVReport(
        @PathVariable Long userId,
        @RequestBody AnalyticsRequest request) {

        String csv = reportService.generateCSVReport(userId, request.getStartDate(), request.getEndDate());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions-report.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(csv);
    }

    @PostMapping("/user/{userId}/summary")
    @Operation(summary = "Generate text summary", description = "Generates a text summary of transactions for the specified period")
    public ResponseEntity<String> generateTextSummary(
        @PathVariable Long userId,
        @RequestBody AnalyticsRequest request) {

        String summary = reportService.generateTextSummary(userId, request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(summary);
    }
}
