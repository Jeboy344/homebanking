package com.ar.bankingonline.api.controllers;

import com.ar.bankingonline.api.dtos.AccountDto;
import com.ar.bankingonline.application.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private AccountService service;

    @Autowired
    public AccountController(AccountService service){
        this.service = service;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){

        // 1) Obtener la lista de todos los DTO user de la DB
        // Agregar el servicio a la implementación del método del controlador
        List<AccountDto> accounts = service.getAccounts();

        // 2) Devolver la lista y enviar como respuesta
        return ResponseEntity.status(200).body(accounts);
    }

    @GetMapping(value="/accounts/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        AccountDto account = service.getAccountById(id);
        return ResponseEntity.status(200).body(account);
    }

    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto dto){
        return ResponseEntity.status(201).body(service.createAccount(dto)); // Redirija hacia el responsable de crear un usuario en la DB
    }

    @PutMapping(value = "/accounts")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto account){
        return ResponseEntity.status(200).body(service.updateAccount(account));
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.deleteAccount(id));
    }
}
