package com.financetracker.services.Account;

import com.financetracker.dto.account.AccountRequest;
import com.financetracker.dto.account.AccountResponse;
import com.financetracker.entity.Account;
import com.financetracker.entity.User;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.AccountRepository;
import com.financetracker.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(
            account.getId(),
            account.getName(),
            account.getAccountType(),
            account.getBalance(),
            account.getCreatedAt(),
            account.getUpdatedAt()
        );
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        User currentUser = UserUtils.getCurrentUser();

        Account account = new Account();
        account.setName(request.name());
        account.setAccountType(request.accountType());
        account.setBalance(request.initialBalance());
        account.setUser(currentUser);

        Account savedAccount = accountRepository.save(account);
        return mapToResponse(savedAccount);
    }

    public List<AccountResponse> getAllAccounts() {
        User currentUser = UserUtils.getCurrentUser();
        return accountRepository.findAllByUser(currentUser)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        Account account = accountRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));
        return mapToResponse(account);
    }

    @Transactional
    public AccountResponse updateAccount(UUID id, AccountRequest request) {
        User currentUser = UserUtils.getCurrentUser();
        Account account = accountRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));

        account.setName(request.name());
        account.setAccountType(request.accountType());

        // Note: Balance is typically managed only through transactions.
        // We update the name and type.

        Account updatedAccount = accountRepository.save(account);
        return mapToResponse(updatedAccount);
    }

    @Transactional
    public void deleteAccount(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        Account account = accountRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));

        accountRepository.delete(account);
    }

    // Helper method for TransactionService to retrieve Account entity
    public Account findAccountEntity(UUID id, User user) {
        return accountRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + id));
    }
}