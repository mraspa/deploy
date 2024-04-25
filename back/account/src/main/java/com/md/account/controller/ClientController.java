package com.md.account.controller;

import com.md.account.documentation.ClientApi;
import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;
import com.md.account.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ClientController implements ClientApi {
    private final ClientService clientService;


    @PostMapping("/save-clients")
    @Override
    public ResponseEntity<HttpStatus> saveClients(@Valid @RequestBody ClientDtoRequest clientDtoRequest) throws ClientAlreadyExistsException {
        clientService.saveClient(clientDtoRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/alias-cbu")
    @Override
    public ResponseEntity<AliasCBUDtoResponse> getAliasCbu(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String request) {
        return new ResponseEntity<>(clientService.getAliasCBU(request), HttpStatus.OK);
    }

    @GetMapping("/name")
    @Override
    public ResponseEntity<String> getName(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String request) {

        return ResponseEntity.ok(clientService.getName(request));
    }

    @GetMapping("/balance")
    @Override
    public ResponseEntity<BigDecimal> getBalance(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String request) {

        return ResponseEntity.ok(clientService.getBalance(request));
    }

    @GetMapping("/client")
    @Override
    public ResponseEntity<ClientDtoResponse> getClient(@Valid @RequestBody AliasOrCBUDtoRequest aliasOrCBUDtoRequest) {
        return new ResponseEntity<>(clientService.getClient(aliasOrCBUDtoRequest), HttpStatus.OK);
    }

    @GetMapping("/client-dni")
    @Override
    public ResponseEntity<ClientDtoResponse> getClientByDocumentNumber(@Valid @RequestBody DocumentNumberDtoRequest documentNumber) {
        return new ResponseEntity<>(clientService.getClientByDocumentNumber(documentNumber), HttpStatus.OK);
    }
}
