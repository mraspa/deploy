package com.md.onboarding.documentation;

import com.md.onboarding.model.dto.ClientDetailRequest;
import com.md.onboarding.model.dto.ValidateMailRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OnboardingApi {

    @Operation(
            description = "A new client is created with the data received.",
            summary = "Create a new client.")
    @ApiResponses(value = {
            @ApiResponse(description = "Client created", responseCode = "201")})
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ClientDetailRequest clientDetailRequest);

    @Operation(
            description = "It checks if the number of a client's identity document is already registered.",
            summary = "Check id number")
    @ApiResponses(value = {
            @ApiResponse(description = "It returns TRUE if it exists and FALSE if it doesn't exist", responseCode = "200")})
    @GetMapping("/check-id/{idNumber}")
    public ResponseEntity<Boolean> checkIdNumber(@PathVariable("idNumber") Long idNumber);

    @Operation(
            description = "It checks whether a client's cuil number is already registered.",
            summary = "Check a client's cuil number")
    @ApiResponses(value = {
            @ApiResponse(description = "It returns TRUE if it exists and FALSE if it doesn't exist", responseCode = "200")})
    @GetMapping("/check-cuil/{cuil}")
    public ResponseEntity<Boolean> checkCuil(@PathVariable("cuil") String cuil);

    @Operation(
            description = "It checks whether a client's email is already registered.",
            summary = "Check a client's email")
    @ApiResponses(value = {
            @ApiResponse(description = "It returns TRUE if it exists and FALSE if it doesn't exist", responseCode = "200")})
    @GetMapping("/check-mail/{mail}")
    public ResponseEntity<Boolean> checkMail(@PathVariable("mail") String mail);

    @Operation(
            description = "It checks whether a client's phone number is already registered.",
            summary = "Check a client's phone number")
    @ApiResponses(value = {
            @ApiResponse(description = "It returns TRUE if it exists and FALSE if it doesn't exist", responseCode = "200")})
    @GetMapping("/check-phoneNumber/{phoneNumber}")
    public ResponseEntity<Boolean> checkPhoneNumber(@PathVariable("phoneNumber") String phoneNumber);

    @Operation(
            description = "Retrieves a Strings list of the marital status enums",
            summary = "Returns all the marital status types")
    @ApiResponses(value = {
            @ApiResponse(description = "", responseCode = "200")})
    @GetMapping("/enums/marital-status")
    public ResponseEntity<List<String>> getMaritalStatusStrings();

    @Operation(
            description = "Retrieves a Strings list of the gender types",
            summary = "Returns all the gender types")
    @ApiResponses(value = {
            @ApiResponse(description = "", responseCode = "200")})
    @GetMapping("/enums/gender-types")
    public ResponseEntity<List<String>> getGenderTypeStrings();
    @Operation(
            description = "Retrieves a Strings list of the Identity Copy types",
            summary = "Returns all the identity copy types")
    @ApiResponses(value = {
            @ApiResponse(description = "", responseCode = "200")})
    @GetMapping("/enums/identity-document-copies")
    public ResponseEntity<List<String>> getIdentityDocumentCopyStrings();

    @Operation(
            description = "Retrieves a Strings list of the jpb types",
            summary = "Returns all the job types")
    @ApiResponses(value = {
            @ApiResponse(description = "", responseCode = "200")})
    @GetMapping("/enums/job-types")
    public ResponseEntity<List<String>> getJobTypeStrings();
}
