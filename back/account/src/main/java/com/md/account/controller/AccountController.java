package com.md.account.controller;

import com.md.account.documentation.AccountApi;
import com.md.account.model.dto.BalanceDtoRequest;
import com.md.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountService accountService;

    @PutMapping("/update-balance")
    @Override
    public ResponseEntity<HttpStatus> updateBalance(@Valid @RequestBody BalanceDtoRequest balanceDtoRequest ) {
        accountService.updateBalance(balanceDtoRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
