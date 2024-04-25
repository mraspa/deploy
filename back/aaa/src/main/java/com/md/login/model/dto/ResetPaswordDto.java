package com.md.login.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPaswordDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message = "password must be 8 characters, combine only numbers and letters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+", message = "password should be at least one uppercase letter, one lowercase letter and one number")
    private String password;

    @Size(min = 4, max = 4,message = "code must be 4 digits long")
    private String code;


}
