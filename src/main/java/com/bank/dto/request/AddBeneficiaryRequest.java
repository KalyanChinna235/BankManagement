package com.bank.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddBeneficiaryRequest {

    @NotBlank
    private String ownerAccountNumber;

    @NotBlank
    private String beneficiaryAccountNumber;

    private String nickname;
}