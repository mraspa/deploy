package com.md.login.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @Email(regexp = ".+[@].+[\\.].+",message = "email format is not valid")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message = "password must have 8 characters combining numbers and letters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+", message ="password should have at least one uppercase letter, one lowercase letter and one number")
    private String password;
    @Size(min = 8, max = 8, message = "document number must be 8 digits long")
    @Pattern(regexp = "\\d+", message = "document number must contain only numbers")
    private String documentNumber;
    @Size(min = 11, max = 11,message = "tramit number must be 11 digits long")
    @Pattern(regexp = "\\d+", message = "tramit number must contain only numbers")
    private String tramitNumber;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof UserDto userDto)) return false;
        return Objects.equals(email, userDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
