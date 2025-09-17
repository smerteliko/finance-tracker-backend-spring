package com.financetracker.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Response for validation errors.")
public record ValidationErrorResponse(
    @Schema(description = "The HTTP status code.", example = "400")
    int status,

    @Schema(description = "An overall message for the validation error.", example = "Validation failed for one or more fields.")
    String message,

    @Schema(description = "A map of fields and their corresponding error messages.", example = "{\"email\": \"Email must not be blank\", \"amount\": \"Amount must be a positive number\"}")
    Map<String, String> errors
) {}