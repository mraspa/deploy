package com.md.account.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AliasOrCBUDtoRequest {
    @Size(min=19,max=22, message = "alias or CBU must be between 19 and 22 characters")
    @NotBlank(message = "alias or CBU must not be blank spaces only.")
    private String aliasOrCBU;
}
