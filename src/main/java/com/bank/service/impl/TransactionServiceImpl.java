package com.bank.service.impl;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.TransferRequest;
import com.bank.dto.request.TransferResponse;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.WithdrawResponse;
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
import java.util.Optional;
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
                .referenceNumber(UUID.randomUUID().toString())
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
                .referenceNumber(transaction.getReferenceNumber())
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
                        .referenceNumber(UUID.randomUUID().toString())
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
                .transactionReference(transaction.getReferenceNumber())
                .accountNumber(account.getAccountNumber())
                .withdrawnAmount(request.getAmount())
                .availableBalance(newBalance)
                .status("SUCCESS")
                .build();
    }

    @Transactional
    @Override
    public TransferResponse transfer(TransferRequest request) throws InsufficientBalanceException {

        Account sender = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Account receiver = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal senderNewBalance = sender.getBalance().subtract(request.getAmount());
        BigDecimal receiverNewBalance = receiver.getBalance().add(request.getAmount());

        sender.setBalance(senderNewBalance);
        receiver.setBalance(receiverNewBalance);

        accountRepository.save(sender);
        accountRepository.save(receiver);
        String reference = UUID.randomUUID().toString();

        Transaction debit = Transaction.builder()
                .referenceNumber(reference)
                .accountNumber(sender.getAccountNumber())
                .transactionType(TransactionType.TRANSFER_DEBET)
                .amount(request.getAmount())
                .balanceAfterTransaction(senderNewBalance)
                .status(TransactionStatus.SUCCESS)
                .performedBy(securityUtils.currentUser())
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(debit);

        Transaction credit = Transaction.builder()
                .referenceNumber(reference)
                .accountNumber(receiver.getAccountNumber())
                .transactionType(TransactionType.TRANSFER_CREDIT)
                .amount(request.getAmount())
                .balanceAfterTransaction(receiverNewBalance)
                .status(TransactionStatus.SUCCESS)
                .performedBy(securityUtils.currentUser())
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(credit);
        return TransferResponse.builder()
                .referenceNumber(reference)
                .fromAccount(sender.getAccountNumber())
                .toAccount(receiver.getAccountNumber())
                .transferredAmount(request.getAmount())
                .senderBalance(senderNewBalance)
                .receiverBalance(receiverNewBalance)
                .status("SUCCESS")
                .build();
    }


}
