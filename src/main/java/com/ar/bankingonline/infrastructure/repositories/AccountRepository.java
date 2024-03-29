package com.ar.bankingonline.infrastructure.repositories;

import com.ar.bankingonline.domain.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // ToDo: Agregar una búsqueda por número de cuenta en la base de datos

    /*@Query(nativeQuery = true, value = "SELECT * From account WHERE number = :number")
    Account getAccountByNumber(@Param("number")int number);*/

}
