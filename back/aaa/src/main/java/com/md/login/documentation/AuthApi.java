package com.md.login.documentation;

import com.md.login.exception.UserBlockedException;
import com.md.login.model.dto.AuthResponse;
import com.md.login.model.dto.LoginRequestDto;
import com.md.login.model.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthApi {

    @Operation(
            description = "Create a new client with the received data.",
            summary = "Create a new client."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Client created", responseCode = "201"),
            @ApiResponse(description = "The email is already in use", responseCode = "409")
    })
    @PostMapping
    ResponseEntity<HttpStatus> save(@Valid @RequestBody UserDto userDto);

    @Operation(
            description = "Authenticate user with the provided credentials.",
            summary = "Authenticate user."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Authentication successful", responseCode = "200"),
            @ApiResponse(description = "Authentication failed", responseCode = "401")
    })
    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto request) throws UserBlockedException;
}
