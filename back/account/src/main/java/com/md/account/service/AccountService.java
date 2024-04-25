package com.md.account.service;


import com.md.account.model.dto.BalanceDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.entity.Account;

public interface AccountService {
    Account createAccount(ClientDtoRequest client);

    void updateBalance(BalanceDtoRequest balanceDtoRequest);
}
