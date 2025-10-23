package com.financetracker.controller;

import com.financetracker.dto.error.ErrorResponse;
import com.financetracker.dto.error.ValidationErrorResponse;
import com.financetracker.dto.transaction.TransactionRequest;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.services.Transaction.TransactionService;
import com.financetracker.services.UserServices.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Financial transactions management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/")
    @Operation(summary = "Get all transactions for user")
    public ResponseEntity<List<TransactionResponse>> getUserTransactions( @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userDetails.getUserId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/period")
    @Operation(summary = "Get user transactions by date period")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByPeriod(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(transactionService.getUserTransactionsByPeriod(userDetails.getUserId(), startDate, endDate));
    }

    @PostMapping
    @Operation(summary = "Create a new transaction")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transaction created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "User or Category not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<TransactionResponse> createTransaction(
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody TransactionRequest request
    ) {
        return ResponseEntity.ok(transactionService.createTransaction(request, userDetails.getUserId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction")
    @Schema(hidden = true)
    public ResponseEntity<TransactionResponse> updateTransaction(
        @PathVariable Long id,
        @Valid @RequestBody TransactionRequest request,
        @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, request, userDetails.getUserId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction")
    @Schema(hidden = true)
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
