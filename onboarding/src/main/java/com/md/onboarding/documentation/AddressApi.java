package com.md.onboarding.documentation;

import com.md.onboarding.exception.InvalidInputException;
import com.md.onboarding.model.dto.AddressRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface AddressApi {

    @Operation(
            description = "Verifies that the address entered by the client exists and meets the location conditions." +
                    "At the time (2024/03/18), said conditions are: Address must be in Argentina" +
                    "for further information on the used API please check https://nominatim.org/release-docs/latest/" +
                    "this endpoint only returns a true or false boolean, true meaning the address is in Argentina" +
                    "false meaning the opposite",
            summary =  "Verify the address of the client.")
    @ApiResponses(value = {
            @ApiResponse(description = "The address was successfully verified.", responseCode = "200")})
    @GetMapping()
    public ResponseEntity<Boolean> verifyAddress(
            @RequestBody AddressRequest addressRequest) throws InvalidInputException;

}
