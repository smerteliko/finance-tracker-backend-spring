package com.financetracker.services.Transaction;

import com.financetracker.dto.pagination.PagedResponse;
import com.financetracker.dto.transaction.TransactionFilterRequest;
import com.financetracker.dto.transaction.TransactionRequest;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.entity.Account;
import com.financetracker.entity.Category;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.enums.CategoryType;
import com.financetracker.enums.TransactionType;
import com.financetracker.exception.ResourceNotFoundException;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.services.Account.AccountService;
import com.financetracker.services.Category.CategoryService;
import com.financetracker.services.Pagination.PaginationService;
import com.financetracker.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final PaginationService paginationService; // Injected

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

    /**
     * Adjusts the account balance based on transaction type.
     * @param account The account to modify.
     * @param amount The transaction amount.
     * @param type The category type (INCOME or EXPENSE).
     * @param apply If true, applies the change; if false, reverses it (used for delete/update cleanup).
     */
    private void adjustAccountBalance(Account account, BigDecimal amount, TransactionType type, boolean apply) {
        BigDecimal newBalance;

        if (type == TransactionType.INCOME) {
            newBalance = apply ? account.getBalance().add(amount) : account.getBalance().subtract(amount);
        } else if (type == TransactionType.EXPENSE) {
            newBalance = apply ? account.getBalance().subtract(amount) : account.getBalance().add(amount);
        } else {
            throw new IllegalArgumentException("Unknown CategoryType: " + type);
        }
        account.setBalance(newBalance);
    }

    /**
     * Re-calculates the account balance when a transaction is updated.
     * It reverses the old transaction effect and applies the new one.
     */
    private void rebalanceAccountOnUpdate(Transaction oldTransaction, Transaction newTransaction) {
        adjustAccountBalance(oldTransaction.getAccount(), oldTransaction.getAmount(), oldTransaction.getType(), false);

        adjustAccountBalance(newTransaction.getAccount(), newTransaction.getAmount(), newTransaction.getType(), true);
    }

    // CREATE
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        User currentUser = UserUtils.getCurrentUser();
        Category category = categoryService.findCategoryEntity(request.categoryId(), currentUser);
        Account account = accountService.findAccountEntity(request.accountId(), currentUser);

        Transaction transaction = new Transaction();
        transaction.setAmount(request.amount());
        transaction.setCategory(category);
        transaction.setAccount(account);
        transaction.setType(request.type());
        transaction.setDescription(request.description());
        transaction.setDate(request.date());
        transaction.setUser(currentUser);

        adjustAccountBalance(account, request.amount(), transaction.getType(), true);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToResponse(savedTransaction);
    }

    // READ ALL (Placeholder - needs implementation for filtering/pagination)
    public List<TransactionResponse> getAllTransactions(int page, int size, String sortBy, String sortDir, Object filterRequest) {
        User currentUser = UserUtils.getCurrentUser();
        // Returning a simple list for the current scope. Full implementation needs Pageable and filter logic.
        return transactionRepository.findAllByUser(currentUser).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    // READ ONE
    public TransactionResponse getTransactionById(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
        return mapToResponse(transaction);
    }

    // UPDATE
    @Transactional
    public TransactionResponse updateTransaction(UUID id, TransactionRequest request) {
        User currentUser = UserUtils.getCurrentUser();
        Transaction oldTransaction = transactionRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

        Category newCategory = categoryService.findCategoryEntity(request.categoryId(), currentUser);
        Account newAccount = accountService.findAccountEntity(request.accountId(), currentUser);

        // Save old values for balance reversal
        Transaction tempOldTransaction = new Transaction();
        tempOldTransaction.setAmount(oldTransaction.getAmount());
        tempOldTransaction.setCategory(oldTransaction.getCategory());
        tempOldTransaction.setAccount(oldTransaction.getAccount());

        // Update transaction fields
        oldTransaction.setAmount(request.amount());
        oldTransaction.setCategory(newCategory);
        oldTransaction.setAccount(newAccount);
        oldTransaction.setDescription(request.description());
        oldTransaction.setDate(request.date());

        // Rebalance: Reverse old effect, apply new effect
        rebalanceAccountOnUpdate(tempOldTransaction, oldTransaction);

        Transaction updatedTransaction = transactionRepository.save(oldTransaction);
        return mapToResponse(updatedTransaction);
    }

    // DELETE
    @Transactional
    public void deleteTransaction(UUID id) {
        User currentUser = UserUtils.getCurrentUser();
        Transaction transaction = transactionRepository.findByIdAndUser(id, currentUser)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

        adjustAccountBalance(transaction.getAccount(), transaction.getAmount(), transaction.getType(), false);

        transactionRepository.delete(transaction);
    }

    @Transactional(readOnly = true)
    public PagedResponse<TransactionResponse> getPaginatedTransactions(TransactionFilterRequest filters) {
        User currentUser = UserUtils.getCurrentUser();

        Sort sort = Sort.by(Sort.Direction.fromString(filters.sortOrder()), filters.sortBy());
        Pageable pageable = PageRequest.of(filters.page() - 1, filters.limit(), sort);

        Specification<Transaction> spec = TransactionSpecification.filterTransactions(currentUser, filters);

        Page<Transaction> transactionPage = transactionRepository.findAll(spec, pageable);

        Page<TransactionResponse> responsePage = transactionPage.map(this::mapToResponse);

        return paginationService.createPaginatedResponse(responsePage);
    }
}