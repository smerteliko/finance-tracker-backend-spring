package com.financetracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.financetracker.enums.CategoryType;
import com.financetracker.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_transaction_user_date", columnList = "user_id, date DESC"),
    @Index(name = "idx_transaction_date", columnList = "date"),
    @Index(name = "idx_transaction_type", columnList = "type"),
    @Index(name = "idx_transaction_created", columnList = "created_at")
})
@Comment("Financial transactions table (income/expense)")
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {

    @NotNull
    @Comment("Transaction amount")
    private BigDecimal amount;

    @Size(max = 500)
    @Comment("Transaction description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "transaction_type_enum")
    @Comment("Transaction type: INCOME (income) or EXPENSE (expense)")
    private TransactionType type;

    @NotNull
    @Comment("Transaction date and time")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_transaction_user"))
    @Comment("Reference to the user who owns the transaction")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_transaction_category"))
    @Comment("Reference to the transaction category")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_account"))
    @Comment("Reference to the transaction account")
    private Account account;

}