package com.financetracker.repository;

import com.financetracker.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByUuid(String uuid);
    List<Transaction> findByUserOrderByDateDesc(com.financetracker.entity.User user);
    List<Transaction> findByUserAndDateBetweenOrderByDateDesc(com.financetracker.entity.User user, LocalDateTime start, LocalDateTime end);
    Page<Transaction> findByUser(com.financetracker.entity.User user, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId ORDER BY t.date DESC")
    Page<Transaction> findByUserIdOrderByDateDesc(@Param("userId") Long userId, Pageable pageable);


    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    Page<Transaction> findByUserIdAndDateBetweenOrderByDateDesc(
        @Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    List<Transaction> findByUserIdAndDateBetweenOrderByDateDesc(
        @Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    Page<Transaction> findByUserIdAndTypeAndDateBetweenOrderByDateDesc(
        @Param("userId") Long userId,
        @Param("type") Transaction.TransactionType type,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.category.id = :categoryId AND t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    Page<Transaction> findByUserIdAndCategoryIdAndDateBetweenOrderByDateDesc(
        @Param("userId") Long userId,
        @Param("categoryId") Long categoryId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.type = :type AND t.category.id = :categoryId AND t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    Page<Transaction> findByUserIdAndTypeAndCategoryIdAndDateBetweenOrderByDateDesc(
        @Param("userId") Long userId,
        @Param("type") Transaction.TransactionType type,
        @Param("categoryId") Long categoryId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId AND t.date BETWEEN :start AND :end ORDER BY t.date DESC")
    List<Transaction> findAllByUserIdAndDateBetweenOrderByDateDesc(
        @Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);

    boolean existsById(Long id);
}