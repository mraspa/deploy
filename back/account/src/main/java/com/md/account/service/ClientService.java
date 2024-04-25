package com.md.account.service;


import com.md.account.exception.ClientAlreadyExistsException;
import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;

import java.math.BigDecimal;

public interface ClientService {
    void saveClient(ClientDtoRequest clientDtoRequest) throws ClientAlreadyExistsException;

    AliasCBUDtoResponse getAliasCBU(String documentNumber);

    String getName(String documentNumber);

    BigDecimal getBalance(String documentNumber);

    ClientDtoResponse getClient(AliasOrCBUDtoRequest aliasOrCBUDtoRequest);

    ClientDtoResponse getClientByDocumentNumber (DocumentNumberDtoRequest documentNumber);
}
