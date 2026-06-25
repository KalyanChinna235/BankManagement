package com.bank.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequest {

    @NotNull
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal amount;
}
