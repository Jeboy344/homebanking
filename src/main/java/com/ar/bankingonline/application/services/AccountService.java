package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.mappers.AccountMapper;
import com.ar.bankingonline.domain.models.Account;
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

    public AccountDto getAccountById(Long id) {
        AccountDto account = AccountMapper.AccountToDto(repository.findById(id).get()); // O: .findById(id)).orElse(null);
        return account;
    }

    public AccountDto updateAccount(AccountDto account){
       return AccountMapper.AccountToDto(repository.save(AccountMapper.dtoToAccount(account)));
    }

    public String deleteAccount(Long id){
        Account account = repository.getById(id);
        if (!account.equals(null)){
            repository.deleteById(id);
            return "Se ha eliminado el usuario";
        }else {
            return "No se ha eliminado el usuario";
        }

    }

    // Agregar métodos de ingreso y egreso de dinero y realización de transferencias
}
