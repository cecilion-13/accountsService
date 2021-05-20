package com.harsh.accountsservice.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AccountStatementRequest {
    @Size(min = 14,max = 14)
    private String accountNumber;

}