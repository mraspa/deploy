package com.md.onboarding.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.exception.InvalidInputException;
import com.md.onboarding.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@AllArgsConstructor
@Log4j2
public class AddressServiceImpl implements AddressService {

    private static final String API_URL = "https://nominatim.openstreetmap.org";

    // limit=1 limita la cantidad de resultados, sacarlo si se quieren mas de uno
    private static final String API_URL_SUFFIX = "/search?format=jsonv2&addressdetails=1&limit=1&q=";
    private static final String COUNTRY_CODE = "ar";

    @Override
    public boolean verifyAddress(String streetName, String streetNumber, String town) throws InvalidInputException {

        validateInputs(streetName, streetNumber, town);

        String query = streetName + "+" + streetNumber + "," + town;
        String url = API_URL.concat(API_URL_SUFFIX).concat(query);

        String jsonResponse = new RestTemplate().getForObject(url, String.class);

        return isAddressInCountry(jsonResponse);
    }

    private boolean isAddressInCountry(String jsonResponse){

        ObjectMapper objectMapper = new ObjectMapper();

        // se maneja con jsonNode por que puede traer multiples direcciones
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            for (JsonNode resultNode : jsonNode) {

                String countryCode = resultNode.get("address").get("country_code").asText();
                if (countryCode.equalsIgnoreCase(COUNTRY_CODE)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e);
        }

        return false;
    }

    private void validateInputs(String streetName, String streetNumber, String town) throws InvalidInputException {
        if(streetName.isBlank() || streetNumber.isBlank() || town.isBlank()) {
            throw new InvalidInputException("All addresses must have a name, a number and a town");
        }
    }
}
