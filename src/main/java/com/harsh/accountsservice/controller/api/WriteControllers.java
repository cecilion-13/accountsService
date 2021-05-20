package com.harsh.accountsservice.controller.api;

import com.harsh.accountsservice.controller.request.AccountStatementRequest;
import com.harsh.accountsservice.controller.request.TransferBalanceRequest;
import com.harsh.accountsservice.dto.AccountStatement;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;
import com.harsh.accountsservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class WriteControllers {

    private final AccountService accountService;
    @PostMapping("/create")
    public Account create(@Valid @RequestBody Account account) {
        return accountService.save(account);
    }

    @PostMapping("/sendMoney")
    public Transaction sendMoney(
            @Valid @RequestBody TransferBalanceRequest transferBalanceRequest) {
        return accountService.sendMoney(transferBalanceRequest);
    }
    @DeleteMapping("/delete/{acc_no}")
    public String deleteAccount(@PathVariable("acc_no") String acc_no){
        return accountService.deleteAccountByAccountNumber(acc_no);
    }
}