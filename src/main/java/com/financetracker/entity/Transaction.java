package com.financetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", indexes = {
    @Index(name = "idx_transaction_user_date", columnList = "user_id, date DESC"),
    @Index(name = "idx_transaction_date", columnList = "date"),
    @Index(name = "idx_transaction_type", columnList = "type"),
    @Index(name = "idx_transaction_uuid", columnList = "uuid", unique = true),
    @Index(name = "idx_transaction_created", columnList = "created_at")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {
    @NotNull
    private BigDecimal amount;

    @Size(max = 500)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_transaction_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_transaction_category"))
    private Category category;

    public enum TransactionType {
        INCOME, EXPENSE
    }
}