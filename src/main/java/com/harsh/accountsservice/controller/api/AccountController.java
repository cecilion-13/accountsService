package com.harsh.accountsservice.controller.api;

import com.harsh.accountsservice.controller.request.AccountStatementRequest;
import com.harsh.accountsservice.controller.request.TransferBalanceRequest;
import com.harsh.accountsservice.dto.AccountStatement;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;
import com.harsh.accountsservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    @PostMapping("/create")
    public Account create(@RequestBody Account account) {
        return accountService.save(account);
    }

    @GetMapping("/all")
    public List<Account> all() {
        return accountService.findAll();
    }

    @PostMapping("/sendMoney")
    public Transaction sendMoney(
            @RequestBody TransferBalanceRequest transferBalanceRequest) {
        return accountService.sendMoney(transferBalanceRequest);
    }


    @GetMapping("/statement")
    public AccountStatement getStatement(
            @RequestBody AccountStatementRequest accountStatementRequest){
        return accountService.getStatement(accountStatementRequest.getAccountNumber());
    }

    @GetMapping("/statement/{acc_no}")
    public AccountStatement getStatementDemo1(@PathVariable("acc_no") String acc_no){
        return accountService.getStatement(acc_no);
    }

}