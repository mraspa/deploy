package com.md.account.service.impl;

import com.md.account.exception.AccountNotFoundException;
import com.md.account.model.dto.BalanceDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.entity.Account;
import com.md.account.repository.AccountRepository;
import com.md.account.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final String ENTITY = "001";
    private final String AGENCY = "0001";
    private final BigDecimal BALANCE = new BigDecimal("10000.0");


    @Override
    public Account createAccount(ClientDtoRequest client) {
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .balance(BALANCE)
                .alias(generateAlias(client.getDocumentNumber()))
                .CBU(generateCBU())
                .createdAt(LocalDateTime.now())
                .build();

        accountRepository.save(account);
        return account;
    }


    @Override
    @Transactional
    public void updateBalance(BalanceDtoRequest balanceDtoRequest){

        Optional<Account> optionalReceiverAccount = accountRepository.findByAccountNumber(balanceDtoRequest.getReceiverAccountNumber());
        Optional<Account> optionalSenderAccount = accountRepository.findByAccountNumber(balanceDtoRequest.getSenderAccountNumber());

        if(optionalReceiverAccount.isEmpty() || optionalSenderAccount.isEmpty()){
            throw new  AccountNotFoundException("Account Not Found");
        }

        Account receiverAccount = optionalReceiverAccount.get();
        Account senderAccount = optionalSenderAccount.get();

        receiverAccount.setBalance(receiverAccount.getBalance().add(balanceDtoRequest.getBalance()));
        senderAccount.setBalance(senderAccount.getBalance().subtract(balanceDtoRequest.getBalance()));

        accountRepository.save(receiverAccount);
        accountRepository.save(senderAccount);

    }

    private String generateAccountNumber() {
        Long lastAccountId = accountRepository.findMaxId();
        Long nextAccountId = (lastAccountId != null) ? lastAccountId + 1 : 1;
        return String.format("%015d", nextAccountId);
    }

    private String generateAlias(String dni) {
        return dni + ".mobywallet";
    }

    private String generateCBU() {
        return ENTITY + AGENCY + generateAccountNumber();
    }
}
