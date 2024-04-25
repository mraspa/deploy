package com.md.onboarding.documentation;

import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.model.dto.ClientDetailRequest;
import com.md.onboarding.model.dto.ValidateMailRequest;
import com.md.onboarding.model.entity.IdentityDocument;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ClientDetailApi {

    @Operation(
            description = "The identity document is obtained by the client's number.",
            summary = "Get identity document by id number.")
    @ApiResponses(value = {
            @ApiResponse(description = "Success", responseCode = "200")})
    @GetMapping("/{idNumber}")
    public ResponseEntity<IdentityDocument> getIdentityDocumentByIdNumber(@PathVariable("idNumber") Long idNumber) throws EntityNotFoundException;

}
