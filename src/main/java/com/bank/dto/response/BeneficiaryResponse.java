package com.bank.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BeneficiaryResponse {

    private Long id;
    private String ownerAccountNumber;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private String nickname;
    private LocalDateTime createdAt;
}