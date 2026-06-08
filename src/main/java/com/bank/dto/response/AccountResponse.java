package com.bank.dto.response;

import com.bank.enums.AccountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {


    private Long id;

    private String accountNumber;

    private String accountHolderName;

    private String email;

    private String phoneNumber;

    private String address;

    private AccountType accountType;

    private Double balance;

    private String status;

    private LocalDateTime createdAt;
}
