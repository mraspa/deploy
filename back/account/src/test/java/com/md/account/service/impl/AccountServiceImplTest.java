package com.md.account.service.impl;

import com.md.account.exception.AccountNotFoundException;
import com.md.account.model.entity.Account;
import com.md.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.TestEntityFactory.getAccount;
import static utils.TestEntityFactory.getAnotherAccount;
import static utils.TestEntityFactory.getBalanceDtoRequest;
import static utils.TestEntityFactory.getClientDtoRequest;


@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount() {

        Account account = accountService.createAccount(getClientDtoRequest());

        verify(accountRepository, times(1)).save(account);

    }

    @Test
    void updateBalance() {

        Account senderAccount = getAccount();
        Account receiverAccount = getAnotherAccount();

        when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber())).thenReturn(Optional.of(receiverAccount));
        when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber())).thenReturn(Optional.of(senderAccount));

        accountService.updateBalance(getBalanceDtoRequest());


        verify(accountRepository, times(1)).save(receiverAccount);
        verify(accountRepository, times(1)).save(senderAccount);

        assert (receiverAccount.getBalance().compareTo(BigDecimal.valueOf(10100)) == 0);
        assert (senderAccount.getBalance().compareTo(BigDecimal.valueOf(9900)) == 0);
    }

    @Test
    void updateBalanceShouldThrowAccountNotFoundException() {

        Account senderAccount = getAccount();
        Account receiverAccount = getAnotherAccount();


        when(accountRepository.findByAccountNumber(receiverAccount.getAccountNumber())).thenReturn(Optional.of(receiverAccount));
        when(accountRepository.findByAccountNumber(senderAccount.getAccountNumber())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.updateBalance(getBalanceDtoRequest()));
    }

}