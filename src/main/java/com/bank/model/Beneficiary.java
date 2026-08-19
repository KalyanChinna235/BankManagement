package com.bank.model;

<<<<<<< HEAD
=======

>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "beneficiaries",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_owner_beneficiary_account",
                        columnNames = {
                                "owner_account_number",
                                "beneficiary_account_number"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD

=======
>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
    @Column(name = "owner_account_number", nullable = false)
    private String ownerAccountNumber;

    @Column(name = "beneficiary_account_number", nullable = false)
    private String beneficiaryAccountNumber;

    @Column(name = "beneficiary_name", nullable = false)
    private String beneficiaryName;

    private String nickname;

    private LocalDateTime createdAt;
<<<<<<< HEAD
    
=======
>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
}
