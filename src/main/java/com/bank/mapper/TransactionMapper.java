package com.bank.mapper;

import com.bank.dto.response.TransactionResponse;
import com.bank.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toResponse(Transaction transaction);

}
