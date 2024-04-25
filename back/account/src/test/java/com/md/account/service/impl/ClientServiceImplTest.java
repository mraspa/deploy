package com.md.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.exception.ClientNotFoundException;
import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;
import com.md.account.model.entity.Account;
import com.md.account.model.entity.Client;
import com.md.account.repository.ClientRepository;
import com.md.account.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestEntityFactory.getAccount;
import static utils.TestEntityFactory.getAliasCBUDtoResponse;
import static utils.TestEntityFactory.getAliasOrCBUDtoRequestAlias;
import static utils.TestEntityFactory.getAliasOrCBUDtoRequestCBU;
import static utils.TestEntityFactory.getBalance;
import static utils.TestEntityFactory.getClient;
import static utils.TestEntityFactory.getClientDtoRequest;
import static utils.TestEntityFactory.getClientDtoResponse;
import static utils.TestEntityFactory.getDocumentNumber;
import static utils.TestEntityFactory.getDocumentNumberDtoRequest;
import static utils.TestEntityFactory.getJwt;
import static utils.TestEntityFactory.getName;


@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {
    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountServiceImpl accountService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private ObjectMapper mapper;

    @Test
    void saveClient() throws ClientAlreadyExistsException {

        ClientDtoRequest clientDtoRequest = getClientDtoRequest();
        Client client = getClient();
        Account account = getAccount();

        when(mapper.convertValue(clientDtoRequest, Client.class)).thenReturn(client);
        when(accountService.createAccount(clientDtoRequest)).thenReturn(account);


        clientService.saveClient(clientDtoRequest);


        verify(clientRepository, times(1)).save(client);
        verify(accountService, times(1)).createAccount(clientDtoRequest);

    }

    @Test
    void saveClientShouldThrowClientAlreadyExistsException() {

        ClientDtoRequest clientDtoRequest = getClientDtoRequest();
        when(clientRepository.findByDocumentNumber(clientDtoRequest.getDocumentNumber())).thenReturn(Optional.of(getClient()));


        assertThrows(ClientAlreadyExistsException.class, () -> clientService.saveClient(clientDtoRequest));
    }


    @Test
     void testGetAliasCBU() {

        String jwt = getJwt();
        String documentNumber = getDocumentNumber();
        AliasCBUDtoResponse aliasCBUDtoResponse = getAliasCBUDtoResponse();

        when(jwtUtils.getDocumentNumber(jwt)).thenReturn(documentNumber);
        when(clientRepository.findAliasAndCBUByDocumentNumber(documentNumber)).thenReturn(Optional.of(aliasCBUDtoResponse));

        AliasCBUDtoResponse actualResponse = clientService.getAliasCBU(jwt);

        assertEquals(aliasCBUDtoResponse, actualResponse);
        verify(jwtUtils).getDocumentNumber(jwt);
        verify(clientRepository).findAliasAndCBUByDocumentNumber(documentNumber);
    }

    @Test
     void testGetAliasCBU_ClientNotFound() {

        String jwt = getJwt();
        String documentNumber = getDocumentNumber();

        when(jwtUtils.getDocumentNumber(jwt)).thenReturn(documentNumber);
        when(clientRepository.findAliasAndCBUByDocumentNumber(documentNumber)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getAliasCBU(jwt));
        verify(jwtUtils).getDocumentNumber(jwt);
        verify(clientRepository).findAliasAndCBUByDocumentNumber(documentNumber);
    }

    @Test
     void testGetName() {

        String jwt = getJwt();
        String documentNumber = getDocumentNumber();
        String clientName = getName();
        Client client = getClient();
        client.setName(clientName);

        when(jwtUtils.getDocumentNumber(jwt)).thenReturn(documentNumber);
        when(clientRepository.findByDocumentNumber(documentNumber)).thenReturn(Optional.of(client));

        String actualName = clientService.getName(jwt);

        assertEquals(clientName, actualName);
    }

    @Test
     void testGetBalance() {
        String jwt = getJwt();
        String documentNumber = getDocumentNumber();
        BigDecimal expectedBalance = getBalance();
        Client client = getClient();
        Account account = getAccount();
        account.setBalance(expectedBalance);
        client.setAccount(account);

        when(jwtUtils.getDocumentNumber(jwt)).thenReturn(documentNumber);
        when(clientRepository.findByDocumentNumber(documentNumber)).thenReturn(Optional.of(client));

        BigDecimal actualBalance = clientService.getBalance(jwt);

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
     void testGetClient_ByAlias() {

        AliasOrCBUDtoRequest request = getAliasOrCBUDtoRequestAlias();
        ClientDtoResponse expectedResponse = getClientDtoResponse();

        when(clientRepository.findClientDtoByAlias(request.getAliasOrCBU())).thenReturn(Optional.of(expectedResponse));

        ClientDtoResponse actualResponse = clientService.getClient(request);

        assertEquals(expectedResponse, actualResponse);
        verify(clientRepository).findClientDtoByAlias(request.getAliasOrCBU());
    }

    @Test
     void testGetClient_Alias_NotFound() {

        AliasOrCBUDtoRequest request = getAliasOrCBUDtoRequestAlias();

        when(clientRepository.findClientDtoByAlias(request.getAliasOrCBU())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(request));
        verify(clientRepository).findClientDtoByAlias(request.getAliasOrCBU());
    }

    @Test
     void testGetClient_ByCBU() {

        AliasOrCBUDtoRequest request = getAliasOrCBUDtoRequestCBU();
        ClientDtoResponse expectedResponse = getClientDtoResponse();

        when(clientRepository.findClientDtoByCBU(request.getAliasOrCBU())).thenReturn(Optional.of(expectedResponse));

        ClientDtoResponse actualResponse = clientService.getClient(request);

        assertEquals(expectedResponse, actualResponse);
        verify(clientRepository).findClientDtoByCBU(request.getAliasOrCBU());
    }

    @Test
     void testGetClient_CBU_NotFound() {

        AliasOrCBUDtoRequest request = getAliasOrCBUDtoRequestCBU();

        when(clientRepository.findClientDtoByCBU(request.getAliasOrCBU())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(request));
        verify(clientRepository).findClientDtoByCBU(request.getAliasOrCBU());
    }


    @Test
    void testGetClient_ByDocumentNumber() {
        DocumentNumberDtoRequest documentNumber = getDocumentNumberDtoRequest();
        Client client = getClient();
        ClientDtoResponse expectedResponse = getClientDtoResponse();

        when(clientRepository.findByDocumentNumber(documentNumber.getDocumentNumber())).thenReturn(Optional.ofNullable(client));

        ClientDtoResponse actualResponse = clientService.getClientByDocumentNumber(documentNumber);
        assertEquals(expectedResponse, actualResponse);
        verify(clientRepository).findByDocumentNumber(documentNumber.getDocumentNumber());
    }

    @Test
    void testGetClient_ByDocumentNumberClientNotFound() {
        DocumentNumberDtoRequest documentNumber = getDocumentNumberDtoRequest();

        when(clientRepository.findByDocumentNumber(documentNumber.getDocumentNumber())).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientByDocumentNumber(documentNumber));
        verify(clientRepository).findByDocumentNumber(documentNumber.getDocumentNumber());
    }
}
