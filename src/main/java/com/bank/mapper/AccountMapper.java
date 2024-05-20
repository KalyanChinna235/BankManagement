package com.bank.mapper;

import com.bank.dto.AccountDto;
import com.bank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static Account mapTOAccount(AccountDto accountDto) {

        return new Account(accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getAmmount());
    }

    public static AccountDto mapToAccountDto(Account account) {

        return new AccountDto(account.getId(),
                account.getAccountHolderName(),
                account.getAmmount());
    }
}
