package com.financetracker.repository;

import com.financetracker.entity.Account;
import com.financetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findAllByUser(User user);
    Optional<Account> findByIdAndUser(UUID id, User user);
}