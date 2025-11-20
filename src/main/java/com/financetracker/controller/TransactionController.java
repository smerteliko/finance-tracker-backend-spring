package com.financetracker.controller;

import com.financetracker.dto.pagination.PagedResponse;
import com.financetracker.dto.transaction.TransactionFilterRequest;
import com.financetracker.dto.transaction.TransactionRequest;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.services.Transaction.TransactionService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Transactions", description = "Management of financial transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Retrieve a filtered and paginated list of transactions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = PagedResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter or pagination parameters"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<TransactionResponse>> getAllTransactions(
        @Valid TransactionFilterRequest filters) {

        PagedResponse<TransactionResponse> response = transactionService.getPaginatedTransactions(filters);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve a transaction by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transaction successfully created", content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request (validation error)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Account or Category not found")
    })
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction successfully updated", content = @Content(schema = @Schema(implementation = TransactionResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request (validation error)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Transaction/Account/Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@PathVariable UUID id, @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.updateTransaction(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transaction successfully deleted"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}