package com.bank.service.impl;

import com.bank.dto.request.AccountRequest;
import com.bank.dto.request.FreezeAccountRequest;
import com.bank.dto.request.UnfreezeAccountRequest;
import com.bank.dto.request.UpdateDetailsRequest;
import com.bank.dto.response.AccountResponse;
import com.bank.enums.AccountStatus;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.mapper.AccountMapper;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(AccountRequest request) throws ResourceNotFoundException {

        if (accountRepository.existsByemail(request.getEmail())) {
            throw new ResourceNotFoundException("Account is already exist with this email: " + request.getEmail());
        }
        if (accountRepository.existsByPanNumber(request.getPanNumber())) {
            throw new ResourceNotFoundException("Account is already exist with this pan number: " + request.getPanNumber());
        }
        if (accountRepository.existsByAadharNumber(request.getAadharNumber())) {
            throw new ResourceNotFoundException("Account is already exist with this aadhar number: " + request.getAadharNumber());
        }
        Account account = Account.builder()
                .accountHolderName(request.getAccountHolderName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .accountType(request.getAccountType())
                .balance(request.getBalance())
                .panNumber(request.getPanNumber())
                .aadharNumber(request.getAadharNumber())
                .accountNumber(String.valueOf(generateAccountNumber()))
                .status(AccountStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
        Account save = accountRepository.save(account);

        return accountMapper.toResponse(save);
    }

    @Override
    public AccountResponse getAccountByAccountNumber(String accountNumber) throws ResourceNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByAccountNumber(accountNumber);
        if (accountOptional.isPresent())
            return accountMapper.toResponse(accountOptional.get());
        else throw new ResourceNotFoundException("Account not found with account number " + accountNumber);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();

    }

    @Override
    public AccountResponse updateAccountDetails(String accountNumber, UpdateDetailsRequest request) throws ResourceNotFoundException {
        Optional<Account> accountResponse = accountRepository.findByAccountNumber(accountNumber);
        if (accountResponse.isPresent()) {
            Account account = accountResponse.get();
            account.setAddress(request.getAddress());
            account.setPhoneNumber(request.getPhoneNumber());
            account.setEmail(request.getEmail());
            return accountMapper.toResponse(accountRepository.save(account));
        }
        throw new ResourceNotFoundException("Account not found with account number: " + accountNumber);
    }

    @Override
    public void deleteAccount(String accountNumber) throws ResourceNotFoundException {
        Optional<Account> accountInfo = accountRepository.findByAccountNumber(accountNumber);
        if (accountInfo.isPresent()) {
            Account account = accountInfo.get();
            if (account.getStatus() == AccountStatus.FROZEN) {
                throw new ResourceNotFoundException("Account with account number " + accountNumber + " is frozen and cannot be closed.");
            }
            if (account.getStatus() == AccountStatus.CLOSE) {
                throw new ResourceNotFoundException("Account with account number " + accountNumber + " is already closed.");
            }
            account.setStatus(AccountStatus.CLOSE);
            accountRepository.save(account);
        } else {
            throw new ResourceNotFoundException("Account not found with account number: " + accountNumber);
        }


    }

    @Override
    public AccountResponse freezeAccount(FreezeAccountRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account.getStatus() == AccountStatus.FROZEN) {

            throw new IllegalStateException("Account already frozen");
        }

        account.setStatus(AccountStatus.FROZEN);

        account.setFreezeReason(request.getReason());

        account.setFrozenAt(LocalDateTime.now());

        Account saved = accountRepository.save(account);

        return accountMapper.toResponse(saved);
    }

    @Override
    public AccountResponse unfreezeAccount(UnfreezeAccountRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        if (account.getStatus() == AccountStatus.ACTIVE) {

            throw new IllegalStateException("Account is already active");
        }

        account.setStatus(AccountStatus.ACTIVE);

        account.setFreezeReason(null);

        account.setUnfrozenAt(LocalDateTime.now());

        Account saved = accountRepository.save(account);

        return accountMapper.toResponse(saved);
    }

    private Long generateAccountNumber() {
        Random random = new Random();
        return
                (1000000000L +
                        Math.abs(random.nextLong() % 9000000000L));
    }


}
