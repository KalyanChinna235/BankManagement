package com.bank.service.impl;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.WithdrawResponse;
import com.bank.enums.AccountType;
import com.bank.enums.TransactionStatus;
import com.bank.enums.TransactionType;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.security.jwt.SecurityUtils;
import com.bank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final SecurityUtils securityUtils;

    @Transactional
    @Override
    public DepositResponse deposit(DepositRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        BigDecimal newBalance = account.getBalance().add(request.getAmount());

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionReference(UUID.randomUUID().toString())
                .accountNumber(account.getAccountNumber())
                .transactionType(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .balanceAfterTransaction(newBalance)
                .status(TransactionStatus.SUCCESS)
                .performedBy(securityUtils.currentUser())
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return DepositResponse.builder()
                .transactionReference(transaction.getTransactionReference())
                .accountNumber(account.getAccountNumber())
                .depositedAmount(request.getAmount())
                .availableBalance(newBalance)
                .status("SUCCESS")
                .build();
    }

    @Transactional
    @Override
    public WithdrawResponse withdraw(WithdrawRequest request) throws InsufficientBalanceException {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {

            throw new InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal newBalance = account.getBalance().subtract(request.getAmount());

        account.setBalance(newBalance);

        accountRepository.save(account);

        Transaction transaction =
                Transaction.builder()
                        .transactionReference(UUID.randomUUID().toString())
                        .accountNumber(account.getAccountNumber())
                        .transactionType(TransactionType.WITHDRAW)
                        .amount(request.getAmount())
                        .balanceAfterTransaction(newBalance)
                        .status(TransactionStatus.SUCCESS)
                        .performedBy(securityUtils.currentUser())
                        .transactionDate(LocalDateTime.now())
                        .build();

        transactionRepository.save(transaction);

        return WithdrawResponse.builder()
                .transactionReference(transaction.getTransactionReference())
                .accountNumber(account.getAccountNumber())
                .withdrawnAmount(request.getAmount())
                .availableBalance(newBalance)
                .status("SUCCESS")
                .build();
    }


}
