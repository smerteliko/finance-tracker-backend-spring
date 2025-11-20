package com.financetracker.controller;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.services.Reports.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Data export and structured summaries")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Operation(summary = "Generates and returns a financial report in CSV format for a given period.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the report as a CSV file", content = @Content(mediaType = "text/csv")),
        @ApiResponse(responseCode = "400", description = "Invalid date range or request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/csv")
    public ResponseEntity<String> generateCsv(@Valid @RequestBody AnalyticsRequest request) {

        LocalDate startDate = request.startDate() != null ? request.startDate() : LocalDate.now().minusDays(30);
        LocalDate endDate = request.endDate() != null ? request.endDate() : LocalDate.now();

        String csvContent = reportService.generateCsvReport(startDate, endDate);

        String fileName = String.format(
            "finance_report_%s_to_%s.csv",
            startDate.format(DATE_FORMATTER),
            endDate.format(DATE_FORMATTER)
        );

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("text/csv"))
            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName))
            .body(csvContent);
    }

    @Operation(summary = "Generates and returns a brief text summary of the financial data for a period.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the text summary", content = @Content(schema = @Schema(implementation = Map.class, example = "{\"summary\": \"Financial Summary for the period...\"}"))),
        @ApiResponse(responseCode = "400", description = "Invalid date range or request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/summary")
    public ResponseEntity<Map<String, String>> generateSummary(@Valid @RequestBody AnalyticsRequest request) {

        LocalDate startDate = request.startDate() != null ? request.startDate() : LocalDate.now().minusDays(30);
        LocalDate endDate = request.endDate() != null ? request.endDate() : LocalDate.now();

        String summary = reportService.generateTextSummary(startDate, endDate);

        return ResponseEntity.ok(Map.of("summary", summary));
    }

}