package com.financetracker.repository;

import com.financetracker.dto.analytics.CategorySummary;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.enums.CategoryType;
import com.financetracker.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> , JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findById(UUID id);
    List<Transaction> findByUserOrderByDateDesc(com.financetracker.entity.User user);
    List<Transaction> findByUserAndDateBetweenOrderByDateDesc(com.financetracker.entity.User user, LocalDateTime start, LocalDateTime end);
    Page<Transaction> findByUser(com.financetracker.entity.User user, Pageable pageable);

    Optional<Transaction> findByIdAndUser(UUID id, User user);

    List<Transaction> findAllByUser(User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.category c WHERE t.user = :user AND c.type = com.financetracker.enums.CategoryType.INCOME")
    BigDecimal sumIncome(@Param("user") User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.category c WHERE t.user = :user AND c.type = com.financetracker.enums.CategoryType.EXPENSE")
    BigDecimal sumExpense(@Param("user") User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.category c WHERE t.user = :user AND c.type = com.financetracker.enums.CategoryType.INCOME AND t.date >= :startDate AND t.date < :endDate")
    BigDecimal sumIncomeByPeriod(
        @Param("user") User user,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t JOIN t.category c WHERE t.user = :user AND c.type = com.financetracker.enums.CategoryType.EXPENSE AND t.date >= :startDate AND t.date < :endDate")
    BigDecimal sumExpenseByPeriod(
        @Param("user") User user,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );


    @Query(value = """
        SELECT 
            c.id as category_id, 
            c.name as category_name, 
            c.type as category_type, 
            SUM(t.amount) as total_amount 
        FROM transactions t 
        JOIN categories c ON t.category_id = c.id 
        WHERE t.user_id = :userId 
        AND c.type = CAST(:type AS category_type_enum)
        AND t.date >= :startDate 
        AND t.date < :endDate 
        GROUP BY c.id, c.name, c.type 
        ORDER BY SUM(t.amount) DESC
        """, nativeQuery = true)
    List<Object[]> getCategoryBreakdownNative(
        @Param("userId") UUID userId,
        @Param("type") String type,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    default List<CategorySummary> getCategoryBreakdown(User user, CategoryType type, LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = getCategoryBreakdownNative(user.getId(), type.name(), startDate, endDate);
        return results.stream()
            .map(result -> new CategorySummary(
                (UUID) result[0],           // category_id
                (String) result[1],         // category_name
                CategoryType.valueOf((String) result[2]), // category_type
                (BigDecimal) result[3]      // total_amount
            ))
            .collect(Collectors.toList());
    }

    @Query("SELECT COUNT(t.id) FROM Transaction t WHERE t.user = :user AND t.date >= :startDate AND t.date < :endDate")
    long countByPeriod(
        @Param("user") User user,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    List<Transaction> findByUserAndDateBetween(
        User user,
        LocalDateTime start,
        LocalDateTime end
    );

    boolean existsById(UUID id);
}