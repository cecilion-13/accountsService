package com.harsh.accountsservice.service;

import com.harsh.accountsservice.controller.request.TransferBalanceRequest;
import com.harsh.accountsservice.dto.AccountStatement;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;
import com.harsh.accountsservice.entity.Transactions;
import com.harsh.accountsservice.repository.AccountRepository;
import com.harsh.accountsservice.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.BSONTimestamp;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
     private final AccountRepository accountRepository;
     private final TransactionRepository transactionRepository;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        account.set_id(ObjectId.get());
        accountRepository.save(account);
        return accountRepository.findByAccountNumberEquals(account.getAccountNumber());
    }

    @Override
    public Transaction sendMoney(TransferBalanceRequest transferBalanceRequest) {

        String fromAccountNumber = transferBalanceRequest.getFromAccountNumber();
        String toAccountNumber = transferBalanceRequest.getToAccountNumber();
        BigDecimal amount = transferBalanceRequest.getAmount();

        Account fromAccount = accountRepository.
                findByAccountNumberEquals(fromAccountNumber);

        Account toAccount = accountRepository.
                findByAccountNumberEquals(toAccountNumber);

        if(fromAccount.getCurrentBalance().compareTo(BigDecimal.ONE) == 1
                && fromAccount.getCurrentBalance().compareTo(amount) == 1){

            fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().subtract(amount));
            accountRepository.save(fromAccount);
            toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(amount));
            accountRepository.save(toAccount);

            Transaction senderTransaction = new Transaction(fromAccountNumber, amount,"Debit",new Date());
            Transaction reciTransaction = new Transaction(toAccountNumber, amount,"Credit",new Date());
             createTransaction(reciTransaction);
            return createTransaction(senderTransaction);
        }
        return null;
    }

    @Override
    public AccountStatement getStatement(String accountNumber) {
        Account account = accountRepository.findByAccountNumberEquals(accountNumber);
        List<Transaction> transactionList = transactionRepository.findByAccountNumberEquals(accountNumber);
        Transactions transactions = new Transactions(transactionList);
        return new AccountStatement(account.getCurrentBalance(),transactions);
    }


    public Account findByAccountNumber(String accountNumber){
        Account account = accountRepository.findByAccountNumberEquals(accountNumber);
        return account;
    }

    private Transaction createTransaction(Transaction transaction){
          transaction.set_id(ObjectId.get());
          transactionRepository.save(transaction);
          return transaction;
    }
}
