package com.financetracker.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "categories", indexes = {
    @Index(name = "idx_category_name_type", columnList = "name, type"),
    @Index(name = "idx_category_type", columnList = "type"),
    @Index(name = "idx_category_uuid", columnList = "uuid", unique = true),
    @Index(name = "idx_category_created", columnList = "created_at")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    @NotBlank
    private String name;

    private String color;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    public enum CategoryType {
        INCOME, EXPENSE
    }
}