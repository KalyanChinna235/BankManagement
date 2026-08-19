package com.bank.service.impl;

import com.bank.enums.AccountStatus;
import com.bank.exception.AccountFrozenException;
import com.bank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountValidator {

    public void validateActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountFrozenException("Account is not active");
        }
    }
}