package com.bank.service;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.TransferRequest;
import com.bank.dto.request.TransferResponse;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.WithdrawResponse;
import com.bank.exception.InsufficientBalanceException;

public interface TransactionService {

    DepositResponse deposit(DepositRequest request);

    WithdrawResponse withdraw(WithdrawRequest request) throws InsufficientBalanceException;

    TransferResponse transfer(TransferRequest request) throws InsufficientBalanceException;
}
