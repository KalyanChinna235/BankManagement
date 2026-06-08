package com.bank.service.impl;

import com.bank.dto.request.AccountRequest;
import com.bank.dto.response.AccountResponse;
import com.bank.mapper.AccountMapper;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(AccountRequest request) {

        if (accountRepository.existsByemail(request.getEmail())) {
            throw new RuntimeException("Account is already exist with this email: " + request.getEmail());
        }
        if (accountRepository.existsByPanNumber(request.getPanNumber())) {
            throw new RuntimeException("Account is already exist with this pan number: " + request.getPanNumber());
        }
        if (accountRepository.existsByAadharNumber(request.getAadharNumber())) {
            throw new RuntimeException("Account is already exist with this aadhar number: " + request.getAadharNumber());
        }
        Account account = Account.builder()
                .accountHolderName(request.getAccountHolderName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .accountType(request.getAccountType())
                .initialDeposit(request.getInitialDeposit())
                .panNumber(request.getPanNumber())
                .aadharNumber(request.getAadharNumber())
                .accountNumber(generateAccountNumber())
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();
        Account save = accountRepository.save(account);

        return accountMapper.toResponse(save);
    }

    @Override
    public AccountResponse getAccountByAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number must not be null or empty");
        }
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (accountOptional.isPresent())
            return accountMapper.toResponse(accountOptional.get());
        else throw new RuntimeException("Account not found with account number: " + accountNumber);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return "AC" +
                (1000000000L +
                        Math.abs(random.nextLong() % 9000000000L));
    }


}
