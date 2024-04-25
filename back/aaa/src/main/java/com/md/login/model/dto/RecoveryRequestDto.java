package com.md.login.model.dto;

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
public class RecoveryRequestDto {

    @Size(min = 8, max = 8,message = "document number must be 8 digits long")
    private String documentNumber;
    @Size(min = 11, max = 11,message = "tramit number must be 11 digits long")
    private String tramitNumber;
}
