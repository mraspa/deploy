package com.md.account.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class AliasCBUDtoResponse {

    @NotEmpty(message = "alias must not be empty")
    @NotBlank(message = "alias must not be blank")
    private String alias;

    @Size(min = 22, max = 22, message = "CBU must be 22 digits long")
    @Pattern(regexp = "\\d+", message = "CBU must be contain only numbers")
    private String CBU;
}
