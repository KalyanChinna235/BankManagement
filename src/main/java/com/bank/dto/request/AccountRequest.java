package com.bank.dto.request;

import com.bank.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

    @NotBlank
    private String accountHolderName;

    @Email
    private String email;

    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid phone number"
    )
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotNull
    private AccountType accountType;

    @DecimalMin(value = "1000.0", inclusive = true)
    private double initialDeposit;

    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
            message = "Invalid PAN"
    )
    private String panNumber;

    @Pattern(
            regexp = "^\\d{12}$",
            message = "Invalid Aadhaar"
    )
    private String aadharNumber;
}
