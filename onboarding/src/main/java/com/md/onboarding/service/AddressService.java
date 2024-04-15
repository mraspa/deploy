package com.md.onboarding.service;

import com.md.onboarding.exception.InvalidInputException;

public interface AddressService {

    public boolean verifyAddress(String streetName, String streetNumber, String town) throws InvalidInputException;

}
