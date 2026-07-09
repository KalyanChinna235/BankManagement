package com.bank.repository;

import com.bank.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumberOrderByTransactionDateDesc(String accountNumber);

    List<Transaction> findTop10ByAccountNumberOrderByTransactionDateDesc(String accountNumber);

    List<Transaction> findByAccountNumberAndTransactionDateBetweenOrderByTransactionDateDesc(
            String accountNumber, LocalDateTime startDate, LocalDateTime endDate);

    Page<Transaction> findByAccountNumber(String accountNumber, Pageable pageable);

}