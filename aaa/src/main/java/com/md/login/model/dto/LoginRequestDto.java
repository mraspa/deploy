package com.md.login.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @Email(regexp = ".+[@].+[\\.].+",message = "mail format is not valid")
    private String email;
    private String password;
}
