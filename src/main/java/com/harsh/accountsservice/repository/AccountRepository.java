package com.harsh.accountsservice.repository;

import com.harsh.accountsservice.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account,Long> {
    Optional<Account> findByAccountNumberEquals(String accountNumber);
    Account findByEmail(String email);
    void deleteByAccountNumber(String accountNUmber);
}
