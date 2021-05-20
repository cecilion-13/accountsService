package com.harsh.accountsservice.exception;

public class AccountDoesNotExistException extends RuntimeException{
    public AccountDoesNotExistException(String accountNumber){
        super("Account with "+accountNumber+" doesn't exist,\nPlease Check your Details");
    }
}
