package com.bank.serviceImpl;

import com.bank.dto.AccountDto;
import com.bank.mapper.AccountMapper;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto getByAccountId(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account is not found with this id: " + id));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapTOAccount(accountDto);
        Account save = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(save);
    }

    @Override
    public List<AccountDto> getAll() {
        List<Account> account = accountRepository.findAll();
        return account.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
    }

    @Override
    public AccountDto deposite(Long id, double ammountAdd) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account is not found with this id: " + id));
        double total = account.getAmmount() + ammountAdd;
        account.setAmmount(total);
        Account savedAc = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAc);
    }

    @Override
    public AccountDto withDraw(Long id, double ammountadd) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account is not found with this id: " + id));
         if (account.getAmmount() < ammountadd){
             throw  new RuntimeException("insufficient amount");
         }
        double total = account.getAmmount() - ammountadd;
        account.setAmmount(total);
        Account saveAc = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(saveAc);
    }
}
