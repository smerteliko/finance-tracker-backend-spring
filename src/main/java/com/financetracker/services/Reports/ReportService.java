package com.financetracker.services.Reports;

import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.services.Transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionService transactionService;

    public String generateCSVReport(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<TransactionResponse> transactions = transactionService.getUserTransactionsByPeriod(userId, startDate, endDate);

        StringBuilder csv = new StringBuilder();
        csv.append("Date,Type,Description,Amount,Category\n");

        for (TransactionResponse transaction : transactions) {
            csv.append(transaction.getDate()).append(",")
                .append(transaction.getType()).append(",")
                .append("\"").append(transaction.getDescription()).append("\",")
                .append(transaction.getAmount()).append(",")
                .append(transaction.getCategoryName()).append("\n");
        }

        return csv.toString();
    }

    public String generateTextSummary(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<TransactionResponse> transactions = transactionService.getUserTransactionsByPeriod(userId, startDate, endDate);

        long incomeCount = transactions.stream()
            .filter(t -> t.getType().name().equals("INCOME"))
            .count();

        long expenseCount = transactions.stream()
            .filter(t -> t.getType().name().equals("EXPENSE"))
            .count();

        return String.format(
            "Financial Report\nPeriod: %s to %s\nTotal Transactions: %d\nIncome Transactions: %d\nExpense Transactions: %d",
            startDate.toLocalDate(), endDate.toLocalDate(), transactions.size(), incomeCount, expenseCount
        );
    }
}
