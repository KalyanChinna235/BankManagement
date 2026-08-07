package com.bank.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FreezeAccountRequest {

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String reason;
}
