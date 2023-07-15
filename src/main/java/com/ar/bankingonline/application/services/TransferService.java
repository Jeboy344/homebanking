package com.ar.bankingonline.application.services;

import com.ar.bankingonline.infrastructure.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;

    public TransferService(TransferRepository repository){
        this.repository = repository;
    }

    //ToDo: Completar el CRUD del Service
}
