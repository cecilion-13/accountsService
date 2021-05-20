package com.harsh.accountsservice.exception;

public class InSufficientBalanceException extends RuntimeException{
    public InSufficientBalanceException(){
        super("Balance Insufficient \nCheck your Balance first!");
    }
}
