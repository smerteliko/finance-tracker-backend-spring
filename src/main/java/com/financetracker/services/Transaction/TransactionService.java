package com.financetracker.services.Transaction;

import com.financetracker.dto.TransactionRequest;
import com.financetracker.dto.TransactionResponse;
import com.financetracker.entity.Category;
import com.financetracker.entity.Transaction;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.repository.UserRepository;
import com.financetracker.services.Category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    public List<TransactionResponse> getUserTransactions(Long userId) {
        return transactionRepository.findByUserIdOrderByDateDesc(userId)
            .stream()
            .map(this::enrichTransactionWithCategoryData)
            .collect(Collectors.toList());
    }

    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return enrichTransactionWithCategoryData(transaction);
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        // Validate category exists
        categoryService.getCategoryById(request.getCategoryId());
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setType(request.getType());
        transaction.setDate(request.getDate());
        transaction.setUser(userRepository.getReferenceById(request.getUserId()));
        transaction.setCategory(categoryService.getCategoryById(request.getCategoryId()));


        Transaction savedTransaction = transactionRepository.save(transaction);
        return enrichTransactionWithCategoryData(savedTransaction);
    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        // Validate transaction exists
        Transaction existingTransaction = transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Validate category exists
        categoryService.getCategoryById(request.getCategoryId());

        existingTransaction.setAmount(request.getAmount());
        existingTransaction.setDescription(request.getDescription());
        existingTransaction.setType(request.getType());
        existingTransaction.setDate(request.getDate());
        existingTransaction.setCategory(categoryService.getCategoryById(request.getCategoryId()));

        Transaction updatedTransaction = transactionRepository.save(existingTransaction);
        return enrichTransactionWithCategoryData(updatedTransaction);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    public List<TransactionResponse> getUserTransactionsByPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByUserIdAndDateBetweenOrderByDateDesc(userId, startDate, endDate)
            .stream()
            .map(this::enrichTransactionWithCategoryData)
            .collect(Collectors.toList());
    }

    private TransactionResponse enrichTransactionWithCategoryData(Transaction transaction) {
        TransactionResponse response = new TransactionResponse(transaction);
        try {
            Category category = categoryService.getCategoryById(transaction.getCategory().getId());
            response.setCategoryName(category.getName());
            response.setCategoryColor(category.getColor());
        } catch (Exception e) {
            response.setCategoryName("Unknown Category");
            response.setCategoryColor("#CCCCCC");
        }
        return response;
    }
}
