package com.harsh.accountsservice.dto;

import com.harsh.accountsservice.entity.Transaction;
import com.harsh.accountsservice.entity.Transactions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountStatement {
    BigDecimal currentBalance;

    Transactions transactionHistory;
}