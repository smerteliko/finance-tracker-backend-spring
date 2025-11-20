package com.financetracker.dto.category;

import com.financetracker.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request for creating or updating a category")
public record CategoryRequest(
    @Schema(description = "Category name", example = "Groceries")
    @NotBlank(message = "Category name cannot be empty")
    String name,

    @Schema(description = "Category type (INCOME or EXPENSE)", example = "EXPENSE")
    @NotNull(message = "Category type cannot be empty")
    CategoryType type,

    @Schema(description = "Category color", example = "#ffff")
    String color
) {}