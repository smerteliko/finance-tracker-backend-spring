package com.financetracker.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of financial account")
public enum AccountType {
    CASH,
    BANK_ACCOUNT,
    CREDIT_CARD,
    INVESTMENT
}