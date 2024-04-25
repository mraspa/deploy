package com.md.onboarding.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.onboarding.exception.EntityNotFoundException;
import com.md.onboarding.service.ClientDetailService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.md.onboarding.JUtils.Data.givenIdentityDocument;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ClientDetailController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ClientDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientDetailService clientDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getIdentityDocumentByIdNumber_IsOk_ReturnsEntity() throws Exception {
        when(clientDetailService.getIdentityDocumentByIdNumber(any())).thenReturn(givenIdentityDocument());

        ResultActions response = mockMvc.perform(get("/clients/v1/details/1234"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("genderType", CoreMatchers.is(givenIdentityDocument().getGenderType().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getIdentityByIdNumber_ThrowsNotFound() throws Exception {
        when(clientDetailService.getIdentityDocumentByIdNumber(any())).thenThrow(EntityNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/clients/v1/details/1234"));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}