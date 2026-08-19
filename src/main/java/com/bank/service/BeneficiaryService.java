package com.bank.service;

import com.bank.dto.request.AddBeneficiaryRequest;
import com.bank.dto.response.BeneficiaryResponse;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryResponse addBeneficiary(AddBeneficiaryRequest request);

    List<BeneficiaryResponse> getBeneficiaries(String ownerAccountNumber);

    void deleteBeneficiary(Long beneficiaryId, String ownerAccountNumber);
}