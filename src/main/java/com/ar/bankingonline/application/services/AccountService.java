package com.ar.bankingonline.application.services;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.api.mappers.AccountMapper;
import com.ar.bankingonline.domain.exceptions.AccountNotFoundException;
import com.ar.bankingonline.domain.models.Account;
import com.ar.bankingonline.domain.models.User;
import com.ar.bankingonline.infrastructure.repositories.AccountRepository;
import com.ar.bankingonline.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    // Declaro una instancia del repositorio con @Autowired y sin la anotación
    @Autowired
    private AccountRepository repository;
    @Autowired
    private UserRepository userRepository;


    public AccountService(AccountRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<AccountDto> getAccounts(){
        List<Account> accounts = repository.findAll();
        return accounts.stream()
                .map(AccountMapper::AccountToDto)
                .toList();
    }

    @Transactional
    public AccountDto createAccount(AccountDto account){
        Optional<User> user = userRepository.findById(account.getOwner().getId());
        Account accountModel = AccountMapper.dtoToAccount(account);
        accountModel.setOwner(user.get());
        accountModel= repository.save(accountModel);
        AccountDto dto = AccountMapper.AccountToDto(accountModel);
        return dto;
    }

    @Transactional
    public AccountDto getAccountById(Long id) {
        AccountDto account = AccountMapper.AccountToDto(repository.findById(id).get()); // O: .findById(id).orElse(null);
        return account;
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto account) {

        Optional<Account> accountCreated = repository.findById(id);

        if (accountCreated.isPresent()){
            // Me traigo la entidad de base de datos. Esta es la que voy a modificar
            Account entity = accountCreated.get();
            // Valido que información traigo en el request para solo modificar lo de la petición y no toda la entidad
            if (account.getAmount()!=null){
                entity.setBalance(account.getAmount());
            }
            if (account.getOwner()!=null){
                // Si hay un owner en el body me traigo la entidad completa de BD por el id para vincularle el usuario a la cuenta
                User user = userRepository.getReferenceById(account.getOwner().getId());
                if (user!=null){
                    entity.setOwner(user);
                }

            }
            // No hay que usar este mapper ya que te crea un account distinto al de BD y nop tiene los valores del account en cuestión, hay que modificar
            //Account accountUpdated = AccountMapper.dtoToAccount(account);

            Account saved = repository.save(entity);

            return AccountMapper.AccountToDto(saved);
        } else {
            throw new AccountNotFoundException("Account not found with id: " + id);
        }

    }

    @Transactional
    public String deleteAccount(Long id){

        if (repository.existsById(id)){
            repository.deleteById(id);
            return "Se ha eliminado la cuenta";
        } else {
            return "No se ha eliminado la cuenta";
        }

    }

    // Agregar métodos de ingreso y egreso de dinero y realización de transferencias
    public BigDecimal withdraw(BigDecimal amount, Long idOrigin){
        // primero: Obtenemos la cuenta
        Account account = repository.findById(idOrigin).orElse(null);
        // segundo: debitamos el valor del amount con el amount de esa cuenta (validar si hay dinero disponible)
        if (account.getBalance().subtract(amount).intValue() > 0){
            account.setBalance(account.getBalance().subtract(amount));
            repository.save(account);
        }
        // tercero: devolvemos esa cantidad
        return account.getBalance().subtract(amount);
    }

    public BigDecimal addAmountToAccount(BigDecimal amount, Long idOrigin){
        // primero: Obtenemos la cuenta
        Account account = repository.findById(idOrigin).orElse(null);
        // segundo: acreditamos el valor del amount con el amount de esa cuenta
        account.setBalance(account.getBalance().add(amount));
        repository.save(account);
        // tercero: devolvemos esa cantidad
        return amount;
    }


    // TODO: Crear un método para poder buscar una cuenta por su número (number)
    public Account getAccountByNumber(int number){
        //return repository.getByNumber(number);
        return null;
    }
}
