package com.bank.controller;

import com.bank.dto.AccountDto;
import com.bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        AccountDto accountDto = accountService.getByAccountId(id);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity<AccountDto> createAccountDetails(@Valid @RequestBody AccountDto accountDto) {
        AccountDto save = accountService.createAccount(accountDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllDetails() {
        List<AccountDto> getAll = accountService.getAll();
        return new ResponseEntity<>(getAll, HttpStatus.OK);
    }

    @PutMapping("{id}/deposit")
    public ResponseEntity<AccountDto> depositeAmmount(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        double ammount = request.get("ammount");
        AccountDto save = accountService.deposite(id, ammount);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    @PutMapping("{eid}")
    public ResponseEntity<AccountDto> withDrawAmount(@PathVariable("eid") Long id, @RequestBody Map<String, Double> request) {
        double ammount = request.get("ammount");
        AccountDto save = accountService.withDraw(id, ammount);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
}
