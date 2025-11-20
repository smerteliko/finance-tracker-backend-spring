package com.financetracker.services.Reports;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.dto.analytics.AnalyticsResponse;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.services.Analytics.AnalyticsService;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final AnalyticsService analyticsService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getAmount(),
            transaction.getDescription(),
            transaction.getType(),
            transaction.getDate(),
            transaction.getCategory().getId(),
            transaction.getAccount().getId(),
            transaction.getCreatedAt(),
            transaction.getUpdatedAt()
        );
    }

    public List<Transaction> findTransactionsForPeriod(LocalDate startDate, LocalDate endDate) {
        User currentUser = UserUtils.getCurrentUser();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        return transactionRepository.findByUserAndDateBetween(
            currentUser,
            startDateTime,
            endDateTime
        );
    }


    public String generateCsvReport(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = findTransactionsForPeriod(startDate, endDate);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(baos)) {

            writer.println("ID;Date;Type;Amount;Category;Account;Description");

            for (Transaction t : transactions) {
                String row = String.format("%s;%s;%s;%s;%s;%s;%s",
                    t.getId().toString(),
                    t.getDate().format(DATE_FORMATTER),
                    t.getCategory().getType().name(), // Type from CategoryType
                    t.getAmount().toPlainString().replace('.', ','), // Use comma as decimal separator for typical CSV format
                    t.getCategory().getName(),
                    t.getAccount().getName(),
                    t.getDescription() != null ? t.getDescription().replace(";", ",") : ""
                );
                writer.println(row);
            }

            writer.flush();
            return baos.toString(java.nio.charset.StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV report: " + e.getMessage(), e);
        }
    }

    public String generateTextSummary(LocalDate startDate, LocalDate endDate) {
        AnalyticsRequest request = new AnalyticsRequest(startDate, endDate);

        // Get analytics data from the same service used by the controller
        AnalyticsResponse analytics = analyticsService.getAnalyticsForPeriod(request);

        StringBuilder summary = new StringBuilder();

        // Header
        summary.append(String.format("Financial Summary for the period %s to %s:\n",
            analytics.periodStart().format(DateTimeFormatter.ISO_LOCAL_DATE),
            analytics.periodEnd().format(DateTimeFormatter.ISO_LOCAL_DATE)
        ));
        summary.append("========================================\n");

        // Totals
        summary.append(String.format("Total Income: %.2f\n", analytics.totalIncome().floatValue()));
        summary.append(String.format("Total Expense: %.2f\n", analytics.totalExpense().floatValue()));
        summary.append(String.format("Net Balance for Period: %.2f\n", analytics.balance().floatValue()));
        summary.append(String.format("Total Transactions: %d\n\n", analytics.transactionCount()));

        // Expenses Breakdown
        if (!analytics.expensesByCategory().isEmpty()) {
            summary.append("Expenses Breakdown:\n");
            analytics.expensesByCategory().forEach(item ->
                summary.append(String.format("  - %s: %.2f\n", item.categoryName(), item.totalAmount().floatValue()))
            );
            summary.append("\n");
        }

        // Income Breakdown
        if (!analytics.incomeByCategory().isEmpty()) {
            summary.append("Income Breakdown:\n");
            analytics.incomeByCategory().forEach(item ->
                summary.append(String.format("  - %s: %.2f\n", item.categoryName(), item.totalAmount().floatValue()))
            );
        }

        return summary.toString();
    }
}