package com.financetracker.repository;

import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByDateDesc(User user);
    List<Transaction> findByUserAndDateBetweenOrderByDateDesc(User user, LocalDateTime start, LocalDateTime end);
}
