package com.bank.service;

import com.bank.dto.request.AccountRequest;
import com.bank.dto.request.FreezeAccountRequest;
import com.bank.dto.request.UnfreezeAccountRequest;
import com.bank.dto.request.UpdateDetailsRequest;
import com.bank.dto.response.AccountResponse;
import com.bank.exception.ResourceNotFoundException;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request) throws ResourceNotFoundException;

    AccountResponse getAccountByAccountNumber(String accountNumber) throws ResourceNotFoundException;

    List<AccountResponse> getAllAccounts();

    AccountResponse updateAccountDetails(String accountNumber, UpdateDetailsRequest request) throws ResourceNotFoundException;

    void deleteAccount(String accountNumber) throws ResourceNotFoundException;

    AccountResponse freezeAccount(FreezeAccountRequest request);

    AccountResponse unfreezeAccount(UnfreezeAccountRequest request);
}
