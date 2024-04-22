package com.md.login.documentation;

import com.md.login.exception.ExpiredCodeException;
import com.md.login.exception.InvalidCodeException;
import com.md.login.exception.InvalidPasswordException;
import com.md.login.model.dto.MaskedMailDto;
import com.md.login.model.dto.RecoveryRequestDto;
import com.md.login.model.dto.ResetPaswordDto;
import com.md.login.model.dto.ValidateCodeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RecoveryPasswordApi {

    @Operation(
            description = "initialize the process by entering document number and tramit number",
            summary = "Initiate password recovery."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Recovery initiated successfully", responseCode = "200")
    })
    @PostMapping("/recovery")
    ResponseEntity<MaskedMailDto> passwordRecovery(@Valid @RequestBody RecoveryRequestDto recoveryRequestDto) throws InvalidCodeException;


    @Operation(
            description = "Validate the recovery code sent to the user's email.",
            summary = "Validate recovery code."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Code validation successful", responseCode = "200"),
            @ApiResponse(description = "Code has expired", responseCode = "401"),
            @ApiResponse(description = "Invalid code", responseCode = "401")
    })
    @PostMapping("/validate-code")
    ResponseEntity<HttpStatus> validateCode(@Valid @RequestBody ValidateCodeDto validateCodeDto) throws ExpiredCodeException, InvalidCodeException;


    @Operation(
            description = "Reset user's password after successful validation of recovery code.",
            summary = "Reset password."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Password reset successful", responseCode = "200"),
            @ApiResponse(description = "Invalid code", responseCode = "401"),
            @ApiResponse(description = "Invalid password", responseCode = "403")
    })
    @PatchMapping("/reset")
    ResponseEntity<HttpStatus> passwordReset(@Valid @RequestBody ResetPaswordDto resetPaswordDto) throws InvalidPasswordException;

}
