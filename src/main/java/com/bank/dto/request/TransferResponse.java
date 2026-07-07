package com.bank.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {

    private String referenceNumber;

    private String fromAccount;

    private String toAccount;

    private BigDecimal transferredAmount;

    private BigDecimal senderBalance;

    private BigDecimal receiverBalance;

    private String status;

}
