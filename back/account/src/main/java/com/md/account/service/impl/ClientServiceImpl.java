package com.md.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.exception.ClientNotFoundException;
import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;
import com.md.account.model.entity.Client;
import com.md.account.repository.ClientRepository;
import com.md.account.service.ClientService;
import com.md.account.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;
    private final AccountServiceImpl accountService;
    private final ObjectMapper mapper;
    private final JwtUtils jwtUtils;

    @Transactional
    @Override
    public void saveClient(ClientDtoRequest clientDtoRequest) throws ClientAlreadyExistsException {
        validateClient(clientDtoRequest);
        log.info("client mapped");
        Client client = mapper.convertValue(clientDtoRequest, Client.class);
        log.info("creating and assigning account");
        client.setAccount(accountService.createAccount(clientDtoRequest));
        log.info("saving client");
        clientRepository.save(client);

    }

    @Override
    public AliasCBUDtoResponse getAliasCBU(String jwt) {
        String document = jwtUtils.getDocumentNumber(jwt);
        Optional<AliasCBUDtoResponse> aliasCBUDto =
                clientRepository.findAliasAndCBUByDocumentNumber(document);
        if (aliasCBUDto.isEmpty()) {
            throw new ClientNotFoundException("Client Not Found");
        }
        return aliasCBUDto.get();
    }

    @Override
    public String getName(String jwt) {
        String document = jwtUtils.getDocumentNumber(jwt);
        return getClient(document).getName();
    }

    @Override
    public BigDecimal getBalance(String jwt) {
        String document = jwtUtils.getDocumentNumber(jwt);
        return getClient(document).getAccount().getBalance();
    }


    public ClientDtoResponse getClient(AliasOrCBUDtoRequest request) {
        request.setAliasOrCBU(request.getAliasOrCBU().trim());
        if (isAlias(request)) {
            return clientRepository.findClientDtoByAlias(request.getAliasOrCBU()).orElseThrow(() -> new ClientNotFoundException("Client Not Found"));
        }
        return clientRepository.findClientDtoByCBU(request.getAliasOrCBU()).orElseThrow(() -> new ClientNotFoundException("Client Not Found"));
    }

    private boolean isAlias(AliasOrCBUDtoRequest aliasOrCBUDtoRequest) {
        return aliasOrCBUDtoRequest.getAliasOrCBU().contains(".");
    }

    public ClientDtoResponse getClientByDocumentNumber(DocumentNumberDtoRequest documentNumber) {
        Client client = getClient(documentNumber.getDocumentNumber());

        return ClientDtoResponse.builder()
                .accountNumber(client.getAccount().getAccountNumber())
                .cuil(client.getCuil())
                .name(client.getName())
                .lastName(client.getLastName())
                .build();
    }

    private Client getClient(String documentNumber) {
        Optional<Client> client = clientRepository.findByDocumentNumber(documentNumber);
        if (client.isEmpty()) {
            throw new ClientNotFoundException("Client Not Found");
        }
        return client.get();
    }

    private void validateClient(ClientDtoRequest clientDtoRequest) throws ClientAlreadyExistsException {
        log.info("validating client");
        Optional<Client> client = clientRepository.findByDocumentNumber(clientDtoRequest.getDocumentNumber());
        if (client.isPresent()) {
            throw new ClientAlreadyExistsException("client already exists");
        }
    }

}
