package com.md.account.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentNumberDtoRequest {
    @Size(min = 8, max = 8, message = "document number must be 8 digits long")
    @Pattern(regexp = "\\d+", message = "document number must contain only numbers")
    private String documentNumber;
}
