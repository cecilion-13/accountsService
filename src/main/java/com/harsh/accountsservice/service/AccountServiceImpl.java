package com.harsh.accountsservice.service;

import com.harsh.accountsservice.controller.request.TransferBalanceRequest;
import com.harsh.accountsservice.dto.AccountStatement;
import com.harsh.accountsservice.email.model.ReportGenerator;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;
import com.harsh.accountsservice.entity.Transactions;
import com.harsh.accountsservice.exception.AccountAlreadyExistException;
import com.harsh.accountsservice.exception.AccountDoesNotExistException;
import com.harsh.accountsservice.exception.InSufficientBalanceException;
import com.harsh.accountsservice.repository.AccountRepository;
import com.harsh.accountsservice.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ReportGenerator reportGenerator;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        boolean accountExists = accountRepository
                .findByAccountNumberEquals(account.getAccountNumber())
                .isPresent();
        if (accountExists) {
            throw new AccountAlreadyExistException(account.getAccountNumber());
        }
        account.set_id(ObjectId.get());
        accountRepository.save(account);
        return account;
    }

    @Override
    public Transaction sendMoney(TransferBalanceRequest transferBalanceRequest) {
        String fromAccountNumber = transferBalanceRequest.getFromAccountNumber();
        String toAccountNumber = transferBalanceRequest.getToAccountNumber();
        BigDecimal amount = transferBalanceRequest.getAmount();
        String email = transferBalanceRequest.getEmail();

        if (!isValidAccount(fromAccountNumber, email)) {
            throw new AccountDoesNotExistException(fromAccountNumber);
        }
        if (!isValidAccount(toAccountNumber)) {
            throw new AccountDoesNotExistException(toAccountNumber);
        }

        Optional<Account> fromAccountFromRepo = accountRepository.
                findByAccountNumberEquals(fromAccountNumber);
        Account fromAccount = fromAccountFromRepo.get();

        Optional<Account> toAccountFromRepo = accountRepository.
                findByAccountNumberEquals(toAccountNumber);
        Account toAccount = toAccountFromRepo.get();

        if (fromAccount.getCurrentBalance().compareTo(amount) == -1) {
            throw new InSufficientBalanceException();
        }
        if (fromAccount.getCurrentBalance().compareTo(BigDecimal.ONE) == 1
                && fromAccount.getCurrentBalance().compareTo(amount) == 1) {

            fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().subtract(amount));
            accountRepository.save(fromAccount);
            toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(amount));
            accountRepository.save(toAccount);

            Transaction senderTransaction = new Transaction(fromAccountNumber, amount, "Debit", new Date());
            Transaction receiverTransaction = new Transaction(toAccountNumber, amount, "Credit", new Date());
            createTransaction(receiverTransaction);
            reportGenerator.generateInvoice(senderTransaction);
            reportGenerator.generateReceipt(receiverTransaction);
            return createTransaction(senderTransaction);
        }
        return null;
    }

    @Override
    public AccountStatement getStatement(String accountNumber) {
        boolean accountExists = accountRepository
                .findByAccountNumberEquals(accountNumber)
                .isPresent();
        if (!accountExists) {
            throw new AccountDoesNotExistException(accountNumber);
        }
        Optional<Account> accountFromRepo = accountRepository.findByAccountNumberEquals(accountNumber);
        Account account = accountFromRepo.get();
        List<Transaction> transactionList = transactionRepository.findByAccountNumberEquals(accountNumber);
        Transactions transactions = new Transactions(transactionList);
        return new AccountStatement(account.getCurrentBalance(), transactions);
    }


    public Account findByAccountNumber(String accountNumber) {
        if(isValidAccount(accountNumber)) {
            Account account = accountRepository.findByAccountNumberEquals(accountNumber).get();
            return account;
        }else {
            throw new AccountDoesNotExistException(accountNumber);
        }
    }

    public Account getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        return account;
    }

    public String deleteAccountByAccountNumber(String accountNumber) {
        accountRepository.deleteByAccountNumber(accountNumber);
        return "Bank Account with a/c no. " + accountNumber + " is successfully deleted";
    }

    private Transaction createTransaction(Transaction transaction) {
        transaction.set_id(ObjectId.get());
        transactionRepository.save(transaction);
        return transaction;
    }

    private boolean isValidAccount(String accountNumber, String email) {
        Account repoAccount = accountRepository.findByEmail(email);
        if (repoAccount == null) {
            throw new AccountDoesNotExistException(accountNumber);
        }
        if (accountNumber.equalsIgnoreCase(repoAccount.getAccountNumber())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidAccount(String accountNumber) {
        boolean accountExists = accountRepository
                .findByAccountNumberEquals(accountNumber)
                .isPresent();
        return accountExists;
    }
}
