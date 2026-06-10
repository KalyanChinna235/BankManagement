package com.bank.repository;

import com.bank.enums.AccountStatus;
import com.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByemail(String email);

    boolean existsByAadharNumber(String aadharNumber);

    boolean existsByPanNumber(String panNumber);

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByStatus(AccountStatus status);
}
