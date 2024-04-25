package com.md.onboarding.controller;

import com.md.onboarding.documentation.AddressApi;
import com.md.onboarding.exception.InvalidInputException;
import com.md.onboarding.model.dto.AddressRequest;
import com.md.onboarding.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("onboarding/v1/address")
public class AddressController implements AddressApi {

    private final AddressService addressService;

    @GetMapping
    @Override
    public ResponseEntity<Boolean> verifyAddress(
            @RequestBody AddressRequest addressRequest) throws InvalidInputException {
        return new ResponseEntity<>(addressService.verifyAddress(addressRequest.streetName(),
                addressRequest.streetNumber(), addressRequest.town()), HttpStatus.OK);
    }
}
