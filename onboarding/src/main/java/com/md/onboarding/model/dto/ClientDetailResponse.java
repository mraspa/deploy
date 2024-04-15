package com.md.onboarding.model.dto;

public record ClientDetailResponse(
        String firstName,
        String lastName,
        Long cuil
) {
}
