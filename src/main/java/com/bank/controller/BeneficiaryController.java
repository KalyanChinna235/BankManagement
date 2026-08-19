package com.bank.controller;

import com.bank.dto.request.AddBeneficiaryRequest;
import com.bank.dto.response.BeneficiaryResponse;
import com.bank.service.BeneficiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @PostMapping
    public ResponseEntity<BeneficiaryResponse> addBeneficiary(@Valid @RequestBody AddBeneficiaryRequest request) {
        return ResponseEntity.ok(beneficiaryService.addBeneficiary(request));
    }

    @GetMapping("/{ownerAccountNumber}")
    public ResponseEntity<List<BeneficiaryResponse>> getBeneficiaries(@PathVariable String ownerAccountNumber) {
        return ResponseEntity.ok(beneficiaryService.getBeneficiaries(ownerAccountNumber));
    }

    @DeleteMapping("/{ownerAccountNumber}/{beneficiaryId}")
    public ResponseEntity<String> deleteBeneficiary(@PathVariable String ownerAccountNumber, @PathVariable Long beneficiaryId) {

        beneficiaryService.deleteBeneficiary(beneficiaryId, ownerAccountNumber);
        return ResponseEntity.ok("Beneficiary deleted successfully");
    }
}