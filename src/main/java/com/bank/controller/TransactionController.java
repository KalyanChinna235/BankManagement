package com.bank.controller;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.TransferRequest;
import com.bank.dto.request.TransferResponse;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.WithdrawResponse;
import com.bank.exception.InsufficientBalanceException;
import com.bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService
            transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) {

        return ResponseEntity.ok(transactionService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@Valid @RequestBody WithdrawRequest request) throws InsufficientBalanceException {

        return ResponseEntity.ok(transactionService.withdraw(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) throws InsufficientBalanceException {

        return ResponseEntity.ok(transactionService.transfer(request));
    }
}
