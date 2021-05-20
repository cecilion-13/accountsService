package com.harsh.accountsservice.controller.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferBalanceRequest {

    @Size(min = 14,max = 14)
    private String fromAccountNumber;

    @Size(min = 14,max = 14)
    private String toAccountNumber;

    @Positive
    private BigDecimal amount;

    private String email;

}