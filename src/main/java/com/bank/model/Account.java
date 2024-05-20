package com.bank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_details")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name connot be null or empty")
    @Size(min = 0, max = 30)
    @Column(name = "acountholder_name", nullable = false)
    private String accountHolderName;
    @Column(name = "ammout")
    private double ammount;

}
