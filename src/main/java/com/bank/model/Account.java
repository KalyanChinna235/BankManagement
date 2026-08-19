package com.bank.model;

import com.bank.enums.AccountStatus;
import com.bank.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "bank_accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account holder name is required")
    @Size(min = 0, max = 50)
    @Column(name = "acountholder_name", nullable = false)
    private String accountHolderName;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @NotBlank(message = "Email connot be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number connot be null or empty")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10-digit number starting with 6, 7, 8, or 9"
    )
    private String phoneNumber;

    @NotBlank(message = "Address connot be null or empty")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private BigDecimal balance;

    @NotBlank(message = "PAN number is required")
    @Pattern(
            regexp = "^[A-Z]{5}[0-9]{4}[A-Z]$",
            message = "PAN number must be in the format of 5 uppercase letters followed by 4 digits and 1 uppercase letter"
    )
    private String panNumber;

    @NotBlank(message = "Aadhar number is required")
    @Pattern(
            regexp = "^\\d{12}$",
            message = "Aadhaar number must be exactly 12 digits"
    )
    private String aadharNumber;

    private LocalDateTime createdAt;
    
    private String freezeReason;

    private LocalDateTime frozenAt;

    private LocalDateTime unfrozenAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
