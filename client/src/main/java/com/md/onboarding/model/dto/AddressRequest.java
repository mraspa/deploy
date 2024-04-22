package com.md.onboarding.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record AddressRequest(
        @NotEmpty(message = "province is needed")
        String province,
        @NotEmpty(message = "town is needed")
        String town,
        @NotEmpty(message = "postal code is needed")
        String postalCode,
        @NotEmpty(message = "street name is needed")
        String streetName,
        @NotEmpty(message = "street number is needed")
        String streetNumber,
        Integer floorNumber,
        Integer apartmentNumber
) {
}
