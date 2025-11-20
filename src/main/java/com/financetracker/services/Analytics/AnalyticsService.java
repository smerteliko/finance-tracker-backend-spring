package com.financetracker.services.Analytics;

import com.financetracker.dto.analytics.AnalyticsRequest;
import com.financetracker.dto.analytics.AnalyticsResponse;
import com.financetracker.dto.analytics.CategorySummary;
import com.financetracker.dto.analytics.MonthlySummaryResponse;
import com.financetracker.entity.User;
import com.financetracker.enums.CategoryType;
import com.financetracker.enums.TransactionType;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public BigDecimal getCurrentBalance() {
        User user = UserUtils.getCurrentUser();

        BigDecimal income = transactionRepository.sumIncome(user);
        BigDecimal expense = transactionRepository.sumExpense(user);

        return income.subtract(expense);
    }

    @Transactional(readOnly = true)
    public AnalyticsResponse getAnalyticsForPeriod(AnalyticsRequest request) {
        User currentUser = UserUtils.getCurrentUser();

        LocalDate startDate;
        LocalDate endDate;

        if (request == null || request.startDate() == null) {
            startDate = LocalDate.now().minusDays(30);
        } else {
            startDate = request.startDate();
        }

        if (request == null || request.endDate() == null) {
            endDate = LocalDate.now();
        } else {
            endDate = request.endDate();
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();

        BigDecimal totalIncome = transactionRepository.sumIncomeByPeriod(currentUser, startDateTime, endDateTime);
        BigDecimal totalExpense = transactionRepository.sumExpenseByPeriod(currentUser, startDateTime, endDateTime);
        long transactionCount = transactionRepository.countByPeriod(currentUser, startDateTime, endDateTime);

        List<CategorySummary> incomeBreakdown = transactionRepository.getCategoryBreakdown(currentUser, CategoryType.INCOME, startDateTime, endDateTime);
        List<CategorySummary> expenseBreakdown = transactionRepository.getCategoryBreakdown(currentUser, CategoryType.EXPENSE, startDateTime, endDateTime);

        return new AnalyticsResponse(
            totalIncome,
            totalExpense,
            totalIncome.subtract(totalExpense), // Net Balance
            incomeBreakdown,
            expenseBreakdown,
            transactionCount,
            startDateTime,
            endDateTime
        );
    }

}