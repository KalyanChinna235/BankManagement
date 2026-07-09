package com.bank.controller;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.TransferRequest;
import com.bank.dto.request.TransferResponse;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.TransactionResponse;
import com.bank.dto.response.WithdrawResponse;
import com.bank.exception.InsufficientBalanceException;
import com.bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> history(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactions(accountNumber));
    }

    @GetMapping("/{accountNumber}/mini-statement")
    public ResponseEntity<List<TransactionResponse>> miniStatement(@PathVariable String accountNumber) {

        return ResponseEntity.ok(transactionService.getMiniStatement(accountNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<List<TransactionResponse>> search(
            @RequestParam String accountNumber, @RequestParam LocalDate start, @RequestParam LocalDate end) {

        return ResponseEntity.ok(transactionService.getTransactionsByDate(accountNumber, start, end));
    }

    @GetMapping("/{accountNumber}/page")
    public ResponseEntity<Page<TransactionResponse>> page(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "desc")
            String direction) {

        return ResponseEntity.ok(transactionService.getTransactions(accountNumber, page, size, sortBy, direction));
    }
}
