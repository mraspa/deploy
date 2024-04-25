package com.md.onboarding.documentation;

import com.md.onboarding.model.dto.ValidateMailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MailConfirmationApi {

    @Operation(
            description = "Send an email validation token to the client you want to register.",
            summary = "Send client mail confirmation token.")
    @ApiResponses(value = {
            @ApiResponse(description = "Successfully sending the mail confirmation token.", responseCode = "200")})
    @PostMapping("/sendMailConfirmationToken/{mailClient}")
    public ResponseEntity<Boolean> sendMailConfirmationToken(@PathVariable("mailClient") String mailClient) throws Exception;

    @Operation(
            description = "The client's mail is validated against the validation token that was emailed to them.",
            summary = "Validate client mail confirmation token.")
    @ApiResponses(value = {
            @ApiResponse(description = "Successfully validated mail confirmation token.", responseCode = "200")})
    @PostMapping("/validateMailConfirmationToken")
    public ResponseEntity<Boolean> validateMailConfirmationToken(@RequestBody ValidateMailRequest validateMailRequest) throws Exception;

    @Operation(
            description = "Validation of the client's email confirmation token is canceled.",
            summary = "Client mail confirmation token validation successfully canceled.")
    @ApiResponses(value = {
            @ApiResponse(description = "", responseCode = "200")})
    @PostMapping("/cancelMailConfirmationTokenValidation/{mailClient}")
    public ResponseEntity<Void> cancelMailConfirmationTokenValidation(@PathVariable("mailClient") String mailClient) throws Exception;
}
