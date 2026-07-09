package com.bank.dto.response;

import com.bank.enums.TransactionStatus;
import com.bank.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {


    private String transactionReference;

    private String accountNumber;

    private TransactionType transactionType;

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    private TransactionStatus status;

    private String remarks;

    private LocalDateTime transactionDate;
}
