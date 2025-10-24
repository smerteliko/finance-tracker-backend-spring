package com.financetracker.services.Transaction;

import com.financetracker.dto.transaction.TransactionRequest;
import com.financetracker.dto.transaction.TransactionResponse;
import com.financetracker.entity.Category;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.repository.TransactionRepository;
import com.financetracker.repository.UserRepository;
import com.financetracker.services.Category.CategoryService;
import com.financetracker.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TransactionService transactionService;

    private User user;
    private Category category;
    private Transaction transaction;
    private TransactionRequest transactionRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        category = new Category();
        category.setId(1L);
        category.setName("Groceries");
        category.setType(Category.CategoryType.EXPENSE);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(150.0));
        transaction.setType(Transaction.TransactionType.EXPENSE);
        transaction.setDescription("Weekly shopping");
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setDate(LocalDateTime.now());

        transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(BigDecimal.valueOf(150.0));
        transactionRequest.setDescription("Weekly shopping");
        transactionRequest.setType(Transaction.TransactionType.EXPENSE);
        transactionRequest.setCategoryId(1L);
        transactionRequest.setDate(LocalDateTime.now());
    }

    @Test
    void getTransactionById_ShouldReturnTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(categoryService.getCategoryById(1L)).thenReturn(category);

        TransactionResponse response = transactionService.getTransactionById(1L);

        assertNotNull(response);
        assertEquals("Weekly shopping", response.getDescription());
        verify(transactionRepository, times(1)).findById(1L);
    }

//    @Test
//    void createTransaction_ShouldReturnSavedTransaction() {
//        when(userRepository.getReferenceById(1L)).thenReturn(user);
//        when(categoryService.getCategoryById(1L)).thenReturn(category);
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
//
//        TransactionResponse response = transactionService.createTransaction(transactionRequest);
//
//        assertNotNull(response);
//        assertEquals(transactionRequest.getAmount(), response.getAmount());
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//    }

    @Test
    void deleteTransaction_ShouldDeleteTransaction_WhenExists() {
        when(transactionRepository.existsById(1L)).thenReturn(true);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTransaction_ShouldThrowException_WhenNotFound() {
        when(transactionRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> transactionService.deleteTransaction(1L));
        verify(transactionRepository, never()).deleteById(any(Long.class));
    }
}