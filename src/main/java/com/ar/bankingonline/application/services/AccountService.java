package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.dtos.UserDto;
import com.ar.bankingonline.api.mappers.AccountMapper;
import com.ar.bankingonline.api.mappers.UserMapper;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.User;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    // Declaro una instancia del repositorio con @Autowired y sin la anotación
    @Autowired
    private AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }

    public List<AccountDto> getAccounts(){
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::AccountToDto)
                .toList();
    }

    public AccountDto createAccount(AccountDto account){
        return AccountMapper.AccountToDto(repository.save(AccountMapper.dtoToAccount(account)));
    }
}
