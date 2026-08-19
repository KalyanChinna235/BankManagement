package com.bank.service.impl;

import com.bank.dto.request.AddBeneficiaryRequest;
import com.bank.dto.response.BeneficiaryResponse;
<<<<<<< HEAD
import com.bank.service.BeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {
    @Override
    public BeneficiaryResponse addBeneficiary(AddBeneficiaryRequest request) {
        
        return null;
=======
import com.bank.enums.AccountStatus;
import com.bank.exception.AccountFrozenException;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.BeneficiaryAlreadyExistsException;
import com.bank.exception.BeneficiaryNotFoundException;
import com.bank.model.Account;
import com.bank.model.Beneficiary;
import com.bank.repository.AccountRepository;
import com.bank.repository.BeneficiaryRepository;
import com.bank.service.BeneficiaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    private final AccountRepository accountRepository;

    private final AccountValidator accountValidator;

    @Override
    public BeneficiaryResponse addBeneficiary(AddBeneficiaryRequest request) {

        Account ownerAcNumber = accountRepository.findByAccountNumber(request.getOwnerAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Owner A/c not found"));

        accountValidator.validateActive(ownerAcNumber);

//        if (ownerAcNumber.getStatus() == AccountStatus.FROZEN) {
//
//            throw new AccountFrozenException("Account is frozen");
//        }
        Account beneficiaryAc = accountRepository.findByAccountNumber(request.getBeneficiaryAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Beneficiary A/c not found"));

        if (request.getOwnerAccountNumber().equals(request.getBeneficiaryAccountNumber())) {
            throw new IllegalArgumentException("You cannot add your own account as beneficiary");
        }

        boolean exists = beneficiaryRepository.existsByOwnerAccountNumberAndBeneficiaryAccountNumber(
                request.getOwnerAccountNumber(), request.getBeneficiaryAccountNumber()
        );

        if (exists) {
            throw new BeneficiaryAlreadyExistsException("Beneficiary already added");
        }

        Beneficiary beneficiary = Beneficiary.builder()
                .ownerAccountNumber(ownerAcNumber.getAccountNumber())
                .beneficiaryAccountNumber(beneficiaryAc.getAccountNumber())
                .beneficiaryName(beneficiaryAc.getAccountHolderName())
                .nickname(request.getNickname())
                .createdAt(LocalDateTime.now())
                .build();

        Beneficiary saved = beneficiaryRepository.save(beneficiary);

        return BeneficiaryResponse.builder()
                .id(saved.getId())
                .ownerAccountNumber(saved.getOwnerAccountNumber())
                .beneficiaryAccountNumber(saved.getBeneficiaryAccountNumber())
                .beneficiaryName(saved.getBeneficiaryName())
                .nickname(saved.getNickname())
                .createdAt(saved.getCreatedAt())
                .build();
>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
    }

    @Override
    public List<BeneficiaryResponse> getBeneficiaries(String ownerAccountNumber) {
<<<<<<< HEAD
        return null;
=======
        return beneficiaryRepository.findByOwnerAccountNumber(ownerAccountNumber)
                .stream()
                .map(b -> BeneficiaryResponse.builder()
                        .id(b.getId())
                        .ownerAccountNumber(b.getOwnerAccountNumber())
                        .beneficiaryAccountNumber(b.getBeneficiaryAccountNumber())
                        .beneficiaryName(b.getBeneficiaryName())
                        .nickname(b.getNickname())
                        .createdAt(b.getCreatedAt())
                        .build())
                .toList();
>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
    }

    @Override
    public void deleteBeneficiary(Long beneficiaryId, String ownerAccountNumber) {
<<<<<<< HEAD

=======
        Beneficiary beneficiary = beneficiaryRepository
                .findByIdAndOwnerAccountNumber(beneficiaryId, ownerAccountNumber)
                .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiary not found"));

        beneficiaryRepository.delete(beneficiary);
>>>>>>> cb7fff5fc388750e62434d114bf2d6713a5aac98
    }
}
