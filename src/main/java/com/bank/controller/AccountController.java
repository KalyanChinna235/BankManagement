package com.bank.controller;

import com.bank.dto.request.AccountRequest;
import com.bank.dto.response.AccountResponse;
import com.bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<AccountResponse> createAccountDetails(@Valid @RequestBody AccountRequest request) {
        AccountResponse account = accountService.createAccount(request);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountDetailsByAccountNumber(@PathVariable String accountNumber) {
        AccountResponse accountResponse = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
}
