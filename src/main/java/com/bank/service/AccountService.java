package com.bank.service;

import com.bank.dto.request.AccountRequest;
import com.bank.dto.response.AccountResponse;

public interface AccountService {

   AccountResponse createAccount(AccountRequest request);

   AccountResponse getAccountByAccountNumber(String accountNumber);

}
