package com.md.account.documentation;

import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

public interface ClientApi {
    @Operation(
            description = "Saves the client and creates his account",
            summary = "Saves client."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Client saved", responseCode = "201"),
            @ApiResponse(description = "Client already exists", responseCode = "409")
    })
    @PostMapping("/save-clients")
    ResponseEntity<HttpStatus> saveClients(@Valid @RequestBody ClientDtoRequest clientDtoRequest) throws ClientAlreadyExistsException;

    @Operation(
            description = "Obtains the alias and CBU of a client account",
            summary = "Obtains alias and CBU."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Alias and CBU obtained", responseCode = "200"),
            @ApiResponse(description = "Client not found", responseCode = "404")
    })
    @GetMapping("/alias-cbu")
    ResponseEntity<AliasCBUDtoResponse> getAliasCbu(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String request);

    @Operation(
            description = "Obtains the name of a client account",
            summary = "Obtains name."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Name obtained.", responseCode = "200"),
            @ApiResponse(description = "Client not found", responseCode = "404")
    })
    @GetMapping("/name")
    ResponseEntity<String> getName(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String request);

    @Operation(
            description = "Obtains the balance of a client account",
            summary = "Obtains balance."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Balance obtained", responseCode = "200"),
            @ApiResponse(description = "Client not found", responseCode = "404")
    })
    @GetMapping("/balance")
    ResponseEntity<BigDecimal> getBalance(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String request);

    @Operation(
            description = "Obtains the client by Alias or CBU of the client account",
            summary = "Obtains client."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Client obtained", responseCode = "200"),
            @ApiResponse(description = "Client not found", responseCode = "404")
    })
    @GetMapping("/client")
    ResponseEntity<ClientDtoResponse> getClient(@Valid @RequestBody AliasOrCBUDtoRequest aliasOrCBUDtoRequest);
    @Operation(
            description = "Obtains the client by DNI",
            summary = "Obtains client."
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Client obtained", responseCode = "200"),
            @ApiResponse(description = "Client not found", responseCode = "404")
    })
    @GetMapping("/client-dni")
    ResponseEntity<ClientDtoResponse> getClientByDocumentNumber(@Valid @RequestBody DocumentNumberDtoRequest documentNumber);

}
