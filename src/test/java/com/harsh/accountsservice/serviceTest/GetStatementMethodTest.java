package com.harsh.accountsservice.serviceTest;

import com.harsh.accountsservice.controller.request.TransferBalanceRequest;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.repository.AccountRepository;
import com.harsh.accountsservice.repository.TransactionRepository;
import com.harsh.accountsservice.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class GetStatementMethodTest {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Test
    public void getStatement() {
        Account account1 = new Account("1001", "AAAA1111", new BigDecimal(50000),"user1@gmail.com");
        Account account2 = new Account("2002", "AAAA1111",new BigDecimal(2000),"user2@gmial.com");
        accountService.save(account1);
        accountService.save(account2);
        TransferBalanceRequest transferBalanceRequest =
                new TransferBalanceRequest(
                        account1.getAccountNumber(),
                        account2.getAccountNumber(),
                        new BigDecimal(3000),
                        account1.getEmail()
                );

        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.getStatement(account1.getAccountNumber())
                .getCurrentBalance())
                .isEqualTo(new BigDecimal(47000));
        accountService.sendMoney(transferBalanceRequest);
        assertThat(accountService.getStatement(account1.getAccountNumber())
                .getCurrentBalance()).isEqualTo(new BigDecimal(44000));
        assertThat(accountService.getStatement(account1.getAccountNumber())
                .getTransactionHistory().getTransactionList().size()).isEqualTo(2);

        accountService.deleteAccountByAccountNumber(account1.getAccountNumber());
        accountService.deleteAccountByAccountNumber(account2.getAccountNumber());
        transactionRepository.deleteTransactionsByAccountNumber(account1.getAccountNumber());
        transactionRepository.deleteTransactionsByAccountNumber(account2.getAccountNumber());
    }

}

