package com.bank.service;

import com.bank.dto.AccountDto;
import com.bank.model.Account;

import java.util.List;

public interface AccountService {
   public AccountDto getByAccountId(Long id);
   public AccountDto createAccount(AccountDto accountDto);
   public List<AccountDto> getAll();

   public AccountDto deposite(Long id, double ammountAdd);
   public AccountDto withDraw(Long id, double ammountadd);
}
