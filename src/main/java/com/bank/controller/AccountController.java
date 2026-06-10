package com.bank.controller;

import com.bank.dto.request.AccountRequest;

import com.bank.dto.request.UpdateDetailsRequest;
import com.bank.dto.response.AccountResponse;
import com.bank.exception.ResourceNotFoundException;
import com.bank.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<AccountResponse> createAccountDetails(@Valid @RequestBody AccountRequest request) throws ResourceNotFoundException {
        AccountResponse account = accountService.createAccount(request);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("{accountNumber}")
    public ResponseEntity<AccountResponse> getAccountDetailsByAccountNumber(@PathVariable @NotBlank String accountNumber) throws ResourceNotFoundException {
        AccountResponse accountResponse = accountService.getAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @PutMapping("{accountNumber}")
    public ResponseEntity<AccountResponse> updateAccountDetails(@Valid @PathVariable String accountNumber, @RequestBody UpdateDetailsRequest request) throws ResourceNotFoundException {
        AccountResponse accountResponse = accountService.updateAccountDetails(accountNumber, request);
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @DeleteMapping("{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) throws ResourceNotFoundException {
        accountService.deleteAccount(accountNumber);
        return new ResponseEntity<>("Account with account number " + accountNumber + " has been deleted successfully.", HttpStatus.OK);
    }
}
