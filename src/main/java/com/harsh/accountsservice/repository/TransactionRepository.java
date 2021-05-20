package com.harsh.accountsservice.repository;

import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction,String> {
    List<Transaction> findByAccountNumberEquals(String accountNumber);
            void deleteTransactionsByAccountNumber(String accountNumber);
}
