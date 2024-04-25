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
public class ClientDtoRequest {
    @NotEmpty(message = "name must not be empty")
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotEmpty(message = "lastName must not be empty")
    @NotBlank(message = "lastName must not be blank")
    private String lastName;
    @Size(min = 11, max = 11, message = "cuil must be 11 digits long")
    @Pattern(regexp = "\\d+", message = "cuil must be 11 must contain only numbers")
    private String cuil;
    @Size(min = 8, max = 8, message = "document number must be 8 digits long")
    @Pattern(regexp = "\\d+", message = "document number must contain only numbers")
    private String documentNumber;
}
