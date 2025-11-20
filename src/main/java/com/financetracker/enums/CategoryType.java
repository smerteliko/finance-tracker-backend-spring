package com.financetracker.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of financial category, corresponding to transaction flow")
public enum CategoryType {
    INCOME,
    EXPENSE
}