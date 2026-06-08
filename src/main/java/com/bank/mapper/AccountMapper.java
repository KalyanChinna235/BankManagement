package com.bank.mapper;

import com.bank.dto.request.AccountRequest;
import com.bank.dto.response.AccountResponse;
import com.bank.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toResponse(Account account);

    Account toEntity(AccountRequest request);
}
