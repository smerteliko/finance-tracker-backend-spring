package com.financetracker.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "categories", indexes = {
    @Index(name = "idx_category_name_type", columnList = "name, type"),
    @Index(name = "idx_category_type", columnList = "type"),
    @Index(name = "idx_category_uuid", columnList = "uuid", unique = true),
    @Index(name = "idx_category_created", columnList = "created_at")
})
@Comment("Transaction categories table")
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    @NotBlank
    @Comment("Category name")
    private String name;

    @Comment("Category color in HEX format (e.g., #FF5733)")
    private String color;

    @Enumerated(EnumType.STRING)
    @Comment("Category type: INCOME (for income) or EXPENSE (for expenses)")
    private CategoryType type;

    public enum CategoryType {
        INCOME, EXPENSE
    }
}