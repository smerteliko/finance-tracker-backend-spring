package com.financetracker.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String color;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    public enum CategoryType {
        INCOME, EXPENSE
    }
}