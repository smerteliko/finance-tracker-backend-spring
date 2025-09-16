package com.financetracker.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standardized error response format.")
public record ErrorResponse(
    @Schema(description = "The HTTP status code.", example = "400")
    int status,

    @Schema(description = "A descriptive error message.", example = "Invalid request format.")
    String message,

    @Schema(description = "The timestamp when the error occurred.", example = "1678886400000")
    long timestamp
) {}