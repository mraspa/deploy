package com.md.onboarding.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ContactRequest(

        @Email(message = "invalid email address")
        @NotNull (message = "email address is needed")
        String mail,

        @NotNull(message = "phone number is needed")
        String phoneNumber
) {
}
