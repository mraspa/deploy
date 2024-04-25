package com.md.account.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceDtoRequest {
    @Size(min = 15, max = 15, message = "account number be 15 digits long")
    @Pattern(regexp = "\\d+", message = "account number must contain only numbers")
    private String senderAccountNumber;

    @Size(min = 15, max = 15, message = "account number be 15 digits long")
    @Pattern(regexp = "\\d+", message = "account number must contain only numbers")
    private String receiverAccountNumber;

    @PositiveOrZero
    private BigDecimal balance;
}
