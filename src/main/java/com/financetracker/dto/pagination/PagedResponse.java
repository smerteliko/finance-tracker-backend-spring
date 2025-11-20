package com.financetracker.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Standard paginated API response.")
public record PagedResponse<T>(
    @Schema(description = "List of serialized entities (e.g., Transaction or Category).")
    List<T> items,

    @Schema(type = "integer", example = "1")
    int page,

    @Schema(type = "integer", example = "10")
    int limit,

    @Schema(type = "integer", example = "45")
    long totalItems, // Using long for safety

    @Schema(type = "integer", example = "5")
    int totalPages
) {}