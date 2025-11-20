package com.financetracker.services.Transaction;

import com.financetracker.dto.transaction.TransactionFilterRequest;
import com.financetracker.entity.Transaction;
import com.financetracker.entity.User;
import com.financetracker.enums.CategoryType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to build dynamic JPA Specifications (filters) based on TransactionFilterRequest.
 */
public class TransactionSpecification {

    public static Specification<Transaction> filterTransactions(User user, TransactionFilterRequest filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user"), user));

            if (filters.type() != null && !filters.type().isEmpty()) {
                try {
                    CategoryType type = CategoryType.valueOf(filters.type().toUpperCase());
                    predicates.add(criteriaBuilder.equal(root.get("category").get("type"), type));
                } catch (IllegalArgumentException e) {
                    // Log or handle invalid type filter gracefully
                }
            }

            if (filters.accountId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("account").get("id"), filters.accountId()));
            }

            // Ensure distinct results if multiple joins were necessary (though not strictly necessary here)
            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}