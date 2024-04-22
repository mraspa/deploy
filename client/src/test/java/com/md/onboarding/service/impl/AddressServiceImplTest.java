package com.md.onboarding.service.impl;

import com.md.onboarding.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void argentinianAddressShouldReturnTrue() throws InvalidInputException {

        String streetName = "Niceto Vega";

        String streetNumber = "4736";

        String town = "Palermo";

        boolean isInArgentina = addressService.verifyAddress(streetName,streetNumber,town);

        assertTrue(isInArgentina);
    }

    @Test
    void foreignAddressShouldReturnFalse() throws InvalidInputException {

        String streetName = "España";

        String streetNumber = "193";

        String town = "Leonera";

        boolean isInArgentina = addressService.verifyAddress(streetName,streetNumber,town);

        assertFalse(isInArgentina);
    }

    @Test
    void invalidInputsThrowsInvalidInputException(){

        String streetName = "";

        String streetNumber = "";

        String town = "";

        assertThrows(InvalidInputException.class, () ->
                addressService.verifyAddress(streetName,streetNumber,town));
    }


}