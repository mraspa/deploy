package com.md.account.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;
import com.md.account.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.TestEntityFactory.getAliasCBUDtoResponse;
import static utils.TestEntityFactory.getAliasOrCBUDtoRequestAlias;
import static utils.TestEntityFactory.getBalance;
import static utils.TestEntityFactory.getClientDtoRequest;
import static utils.TestEntityFactory.getClientDtoResponse;
import static utils.TestEntityFactory.getDocumentNumberDtoRequest;
import static utils.TestEntityFactory.getJwt;
import static utils.TestEntityFactory.getName;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ClientService clientService;

    @Test
    void saveClients() throws Exception {
        ClientDtoRequest clientDtoRequest = getClientDtoRequest();

        doNothing().when(clientService).saveClient(clientDtoRequest);

        mockMvc.perform(post("/v1/account/save-clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDtoRequest)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
     void testGetAliasCbu() throws Exception {

        String jwt = getJwt();
        AliasCBUDtoResponse expectedResponse = getAliasCBUDtoResponse();

        when(clientService.getAliasCBU(jwt)).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/account/alias-cbu")
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value(expectedResponse.getAlias()))
                .andExpect(jsonPath("$.cbu").value(expectedResponse.getCBU()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
     void testGetName() throws Exception {

        String jwt = getJwt();
        String expectedResponse = getName();

        when(clientService.getName(jwt)).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/account/name")
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
     void testGetBalance() throws Exception {

        String jwt = getJwt();
        BigDecimal expectedResponse = getBalance();

        when(clientService.getBalance(jwt)).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/account/balance")
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse.toString()))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
     void testGetClient() throws Exception {

        AliasOrCBUDtoRequest request = getAliasOrCBUDtoRequestAlias();
        ClientDtoResponse expectedResponse = getClientDtoResponse();

        when(clientService.getClient(any(AliasOrCBUDtoRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/account/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.lastName").value(expectedResponse.getLastName()))
                .andExpect(jsonPath("$.cuil").value(expectedResponse.getCuil()))
                .andExpect(jsonPath("$.accountNumber").value(expectedResponse.getAccountNumber()))
                .andDo(MockMvcResultHandlers.print());


    }


    @Test
    void testGetClientByDocumentNumber() throws Exception {
        DocumentNumberDtoRequest documentNumber = getDocumentNumberDtoRequest();
        ClientDtoResponse expectedResponse = getClientDtoResponse();


        when(clientService.getClientByDocumentNumber(any(DocumentNumberDtoRequest.class))).thenReturn(expectedResponse);
        mockMvc.perform(get("/v1/account/client-dni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(documentNumber)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.lastName").value(expectedResponse.getLastName()))
                .andExpect(jsonPath("$.cuil").value(expectedResponse.getCuil()))
                .andExpect(jsonPath("$.accountNumber").value(expectedResponse.getAccountNumber()))
                .andDo(MockMvcResultHandlers.print());
    }
}




