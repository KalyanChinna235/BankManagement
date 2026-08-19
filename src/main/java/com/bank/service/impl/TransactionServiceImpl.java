package com.bank.service.impl;

import com.bank.dto.request.DepositRequest;
import com.bank.dto.request.TransferRequest;
import com.bank.dto.request.TransferResponse;
import com.bank.dto.request.WithdrawRequest;
import com.bank.dto.response.DepositResponse;
import com.bank.dto.response.TransactionResponse;
import com.bank.dto.response.WithdrawResponse;
import com.bank.enums.AccountStatus;
import com.bank.enums.TransactionStatus;
import com.bank.enums.TransactionType;
import com.bank.exception.AccountFrozenException;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.mapper.TransactionMapper;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import com.bank.security.jwt.SecurityUtils;
import com.bank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final SecurityUtils securityUtils;

    private final TransactionMapper transactionMapper;

    private final AccountValidator accountValidator;

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

        accountValidator.validateActive(account);
//        if (account.getStatus() == AccountStatus.FROZEN) {
//            throw new AccountFrozenException("Account is Frozen");
//        }

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

        accountValidator.validateActive(sender);
//        if (sender.getStatus() == AccountStatus.FROZEN) {
//
//            throw new AccountFrozenException("Sender account is frozen");
//        }

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

    @Override
    public List<TransactionResponse> getTransactions(String accountNumber) {


        return transactionRepository
                .findTop10ByAccountNumberOrderByTransactionDateDesc(accountNumber)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    public List<TransactionResponse> getMiniStatement(String accountNumber) {


        return transactionRepository
                .findTop10ByAccountNumberOrderByTransactionDateDesc(accountNumber)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    public List<TransactionResponse> getTransactionsByDate(String accountNumber, LocalDate start, LocalDate end) {

        return transactionRepository.findByAccountNumberAndTransactionDateBetweenOrderByTransactionDateDesc(
                        accountNumber, start.atStartOfDay(), end.atTime(LocalTime.MAX))
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

    @Override
    public Page<TransactionResponse> getTransactions(String accountNumber, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("des")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return transactionRepository.findByAccountNumber(accountNumber, pageable)
                .map(transactionMapper::toResponse);
    }


}
