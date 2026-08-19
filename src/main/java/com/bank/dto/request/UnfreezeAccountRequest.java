package com.bank.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UnfreezeAccountRequest {

    @NotBlank
    private String accountNumber;
}