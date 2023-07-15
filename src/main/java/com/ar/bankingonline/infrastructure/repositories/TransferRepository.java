package com.ar.bankingonline.infrastructure.repositories;

import com.ar.bankingonline.domain.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
