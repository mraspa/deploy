package com.md.onboarding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.exception.InvalidInputException;
import com.md.onboarding.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.md.onboarding.JUtils.Data.givenAddressRequest;
import static com.md.onboarding.JUtils.Data.givenEmptyAddressRequest;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void verifyAddress_ValidInputs_IsOk() throws Exception {
        String streetName = givenAddressRequest().streetName();
        String streetNumber = givenAddressRequest().streetNumber();
        String town = givenAddressRequest().town();

        when(addressService.verifyAddress(streetName,streetNumber,town)).thenReturn(true);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenAddressRequest())));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void verifyAddress_InvalidInputs_IsBadRequest() throws Exception {
        String streetName = givenEmptyAddressRequest().streetName();
        String streetNumber = givenEmptyAddressRequest().streetNumber();
        String town = givenEmptyAddressRequest().town();

        when(addressService.verifyAddress(streetName,streetNumber,town))
                .thenThrow(InvalidInputException.class);

        ResultActions response = mockMvc.perform(get("/onboarding/v1/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenEmptyAddressRequest())));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}