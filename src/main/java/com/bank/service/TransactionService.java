package com.bank.service;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.TransferRequest;
import com.bank.dto.request.TransferResponse;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.TransactionResponse;
import com.bank.dto.response.WithdrawResponse;
import com.bank.exception.InsufficientBalanceException;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    DepositResponse deposit(DepositRequest request);

    WithdrawResponse withdraw(WithdrawRequest request) throws InsufficientBalanceException;

    TransferResponse transfer(TransferRequest request) throws InsufficientBalanceException;

    List<TransactionResponse> getTransactions(String accountNumber);

    List<TransactionResponse> getMiniStatement(String accountNumber);

    List<TransactionResponse> getTransactionsByDate(String accountNumber, LocalDate start, LocalDate end);

    Page<TransactionResponse> getTransactions(String accountNumber, int page, int size, String sortBy, String direction);
}
