package com.md.onboarding.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ValidateMailRequest(
        @NotEmpty(message = "mailClient is needed")
        String mailClient,
        @NotNull(message = "token is needed")
        @Min(value = 100000, message = "Minimal value is 100000")
        @Max(value = 999999, message = "Maximal value is 999999")
        int token
) {
}
