package com.harsh.accountsservice.service;

import com.harsh.accountsservice.controller.request.TransferBalanceRequest;
import com.harsh.accountsservice.dto.AccountStatement;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    Transaction sendMoney(
            TransferBalanceRequest transferBalanceRequest
    );
    AccountStatement getStatement(String accountNumber);
}
