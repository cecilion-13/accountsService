package com.harsh.accountsservice.serviceTest;

import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.exception.AccountAlreadyExistException;
import com.harsh.accountsservice.repository.AccountRepository;
import com.harsh.accountsservice.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@SpringBootTest
public class SaveMethodTest {
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSave(){
        Account account1 = new Account("1001", "AAAA1111", new BigDecimal(50000),"user1@gmail.com");
        accountService.save(account1);
        Account serviceAccount = accountService.findByAccountNumber(account1.getAccountNumber());
        Account repoAccount = accountRepository.findByAccountNumberEquals(account1.getAccountNumber()).get();
        assertEquals(serviceAccount.toString(),repoAccount.toString());
        accountService.deleteAccountByAccountNumber(account1.getAccountNumber());
    }

    @Test
    public void testSave_whenAccountAlreadyExist(){
        Account account1 = new Account("1001", "AAAA1111", new BigDecimal(50000),"user1@gmail.com");
        accountService.save(account1);
        assertThrows(AccountAlreadyExistException.class,
                ()->{accountService.save(account1);}
        );
        accountService.deleteAccountByAccountNumber(account1.getAccountNumber());
    }
}
