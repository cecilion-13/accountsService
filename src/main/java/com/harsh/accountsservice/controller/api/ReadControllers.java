package com.harsh.accountsservice.controller.api;

import com.harsh.accountsservice.controller.request.AccountStatementRequest;
import com.harsh.accountsservice.dto.AccountStatement;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class ReadControllers {

    private final AccountService accountService;

    @GetMapping("/view_account")
    public List<Account> all() {
        return accountService.findAll();
    }

    @GetMapping("/view_account/{email}")
    public Account getAccountByEmail(
            @PathVariable("email") String email
    ) {
        return accountService.getAccountByEmail(email);
    }

    @GetMapping("/statement")
    public AccountStatement getStatement(
            @Valid @RequestBody AccountStatementRequest accountStatementRequest){
        return accountService.getStatement(accountStatementRequest.getAccountNumber());
    }

    @GetMapping("/statement/{acc_no}")
    public AccountStatement getStatementDemo1(@PathVariable("acc_no") String acc_no){
        return accountService.getStatement(acc_no);
    }

}
