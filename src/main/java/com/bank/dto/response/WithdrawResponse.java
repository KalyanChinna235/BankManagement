package com.bank.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WithdrawResponse {

    private String transactionReference;

    private String accountNumber;

    private BigDecimal withdrawnAmount;

    private BigDecimal availableBalance;

    private String status;
}