package com.financetracker.services.Analytics;

import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.entity.Transaction;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.services.Transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public AnalyticsResponse getUserAnalytics(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<TransactionResponse> transactions = transactionService.getUserTransactionsByPeriod(userId, startDate, endDate);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        Map<String, BigDecimal> expensesByCategory = new HashMap<>();
        Map<String, BigDecimal> incomeByCategory = new HashMap<>();

        for (TransactionResponse transaction : transactions) {
            if (transaction.getType() == Transaction.TransactionType.INCOME) {
                totalIncome = totalIncome.add(transaction.getAmount());
                incomeByCategory.merge(transaction.getCategoryName(), transaction.getAmount(), BigDecimal::add);
            } else {
                totalExpense = totalExpense.add(transaction.getAmount());
                expensesByCategory.merge(transaction.getCategoryName(), transaction.getAmount(), BigDecimal::add);
            }
        }

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new AnalyticsResponse(
            totalIncome,
            totalExpense,
            balance,
            expensesByCategory,
            incomeByCategory,
            transactions.size(),
            startDate,
            endDate
        );
    }

    public MonthlySummaryResponse getMonthlySummary(Long userId, int year, Month month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        AnalyticsResponse analytics = getUserAnalytics(userId, startDate, endDate);

        return new MonthlySummaryResponse(
            analytics,
            month,
            year,
            calculateBudgetRecommendations(analytics)
        );
    }

    private Map<String, BigDecimal> calculateBudgetRecommendations(AnalyticsResponse analytics) {
        Map<String, BigDecimal> recommendations = new HashMap<>();

        // Basic budget recommendation: suggest reducing expenses by 10% in each category
        analytics.expensesByCategory().forEach((category, amount) -> {
            BigDecimal recommended = amount.multiply(BigDecimal.valueOf(0.9));
            recommendations.put(category + "_recommended", recommended);
        });

        return recommendations;
    }

    public record AnalyticsResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        Map<String, BigDecimal> expensesByCategory,
        Map<String, BigDecimal> incomeByCategory,
        int transactionCount,
        LocalDateTime periodStart,
        LocalDateTime periodEnd
    ) {}

    public record MonthlySummaryResponse(
        AnalyticsResponse analytics,
        Month month,
        int year,
        Map<String, BigDecimal> budgetRecommendations
    ) {}
}
