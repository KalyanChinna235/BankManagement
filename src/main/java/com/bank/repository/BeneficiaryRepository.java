package com.bank.repository;

import com.bank.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    List<Beneficiary> findByOwnerAccountNumber(String ownerAccountNumber);

<<<<<<< HEAD
    boolean existsByOwnerAccountNumberAndBeneficiaryAccountNumber(String OwnerAccountNumber, String beneficiaryAccountNumber);

    Optional<Beneficiary> findByIdAndOwnerAccountNumber(Long id, String ownerAccountNumber);
}
=======
    boolean existsByOwnerAccountNumberAndBeneficiaryAccountNumber(String ownerAccountNumber, String beneficiaryAccountNumber);

    Optional<Beneficiary> findByIdAndOwnerAccountNumber(Long id, String ownerAccountNumber);
}
>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
