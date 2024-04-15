package com.md.onboarding.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record IdentityDocumentRequest(
        @NotNull(message = "identity document number is needed")
        @Min(value = 1, message = "Minimal value is 1")
        Long idNumber,
        @NotNull(message = "tramit number is needed")
        @Min(value = 1, message = "Minimal value is 1")
        Long tramitNumber,
        @NotNull(message = "gender type is needed")
        @Pattern(regexp = "^(MALE|FEMALE|X)$", message = "invalid gender type")
        String genderType,

        @NotNull(message = "identity document number is needed")
        @Pattern(regexp = "^(A|B|C|D|E)$" , message = "invalid copy type")
        String identityDocumentCopyType
) {
}
