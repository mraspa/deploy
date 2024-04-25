package com.md.account.documentation;

import com.md.account.model.dto.BalanceDtoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AccountApi {
    @Operation(
            description = "Updates the balance of the account sender and the account receiver",
            summary = "Updates balance."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Balance updated", responseCode = "200"),
            @ApiResponse(description = "Account not found", responseCode = "404")
    })
    @PutMapping("/update-balance")
    ResponseEntity<HttpStatus> updateBalance(@Valid @RequestBody BalanceDtoRequest balanceDtoRequest);
}
