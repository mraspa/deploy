package com.md.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDtoResponse {
    private String name;
    private String lastName;
    private String cuil;
    private String accountNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDtoResponse that = (ClientDtoResponse) o;
        return Objects.equals(cuil, that.cuil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuil);
    }
}
