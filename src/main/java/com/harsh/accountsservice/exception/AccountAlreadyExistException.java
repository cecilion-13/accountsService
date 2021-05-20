package com.harsh.accountsservice.exception;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(String accountNUmber) {
        super("Account with "+accountNUmber+" " +
                "Already Exists. \nPlease Check your Details!");
    }

    public AccountAlreadyExistException(){
        super("Account Already Exists. \nPlease Check your Details!");
    }

}

