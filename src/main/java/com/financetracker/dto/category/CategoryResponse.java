package com.financetracker.dto.category;

import com.financetracker.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response containing category information")
public record CategoryResponse(
    @Schema(description = "Unique category identifier")
    UUID id,

    @Schema(description = "Category name", example = "Groceries")
    String name,

    @Schema(description = "Category type (INCOME or EXPENSE)", example = "EXPENSE")
    CategoryType type,

    @Schema(description = "Category color", example = "color")
    String color,

    @Schema(description = "Creation timestamp")
    LocalDateTime createdAt,

    @Schema(description = "Last update timestamp")
    LocalDateTime updatedAt
) {}