package com.bank.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DepositResponse {

    private String referenceNumber;

    private String accountNumber;

    private BigDecimal depositedAmount;

    private BigDecimal availableBalance;

    private String status;
}
