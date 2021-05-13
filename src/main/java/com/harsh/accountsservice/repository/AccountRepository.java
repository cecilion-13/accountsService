package com.harsh.accountsservice.repository;

import com.harsh.accountsservice.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,Long> {
    Account findByAccountNumberEquals(String accountNumber);
}