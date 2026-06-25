package com.bank.model;

import com.bank.enums.TransactionStatus;
import com.bank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String transactionReference;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String performedBy;

    private LocalDateTime transactionDate;
}
