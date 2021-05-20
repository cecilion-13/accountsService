package com.harsh.accountsservice.advice;

import com.harsh.accountsservice.exception.AccountAlreadyExistException;
import com.harsh.accountsservice.exception.AccountDoesNotExistException;
import com.harsh.accountsservice.exception.InSufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BankAccountAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }


    @ResponseBody
    @ExceptionHandler(AccountDoesNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String accountDoesNotExistHandler(AccountDoesNotExistException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AccountAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String accountAlreadyExistHandler(AccountAlreadyExistException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InSufficientBalanceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String insufficientBalanceHandler(InSufficientBalanceException ex){
        return ex.getMessage();
    }

    /**
    @ResponseBody
    @ExceptionHandler(AccountAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<AccountAlreadyExistException>
    accountAlreadyExistHandlerDemo(AccountAlreadyExistException ex){
        AccountAlreadyExistException exception = new
                AccountAlreadyExistException();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(exception);
    }
    **/
}
