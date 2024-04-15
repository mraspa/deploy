package com.md.onboarding.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ClientDetailRequest(
        @NotEmpty(message = "first name is needed")
        String firstName,
        @NotEmpty(message = "lastName  is needed")
        String lastName,
        @NotNull(message = "birthDate is needed")
        LocalDate birthDate,
        @NotNull(message = "maritalStatus is needed")
        @Pattern(regexp = "^(SINGLE|MARRIED|WIDOWED|DIVORCED|SEPARATED|REGISTEREDPARTNERSHIP)$", message = "invalid marital status")
        String maritalStatus,
        @NotNull(message = "job type is needed")
        @Pattern(regexp = "^(EMPLOYED|UNEMPLOYED|STUDENT|STAYATHOME|FREELANCE)$", message = "invalid job type")
        String jobType,
        @NotNull(message = "cuil is needed")
        @Min(value = 1, message = "Minimal value is 1")
        Long cuil,
        @NotNull(message = "enabled is needed")
        boolean enabled,
        @NotNull(message = "isPep is needed")
        boolean isPep,
        @NotNull(message = "addressRequest is needed")
        @Valid
        AddressRequest addressRequest,
        @NotNull(message = "identityDocumentRequest is needed")
        @Valid
        IdentityDocumentRequest identityDocumentRequest,
        @NotNull(message = "contactRequest is needed")
        @Valid
        ContactRequest contactRequest

) {
}
