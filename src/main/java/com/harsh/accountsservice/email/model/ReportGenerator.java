package com.harsh.accountsservice.email.model;

import com.harsh.accountsservice.email.config.EmailConfig;
import com.harsh.accountsservice.entity.Account;
import com.harsh.accountsservice.entity.Transaction;
import com.harsh.accountsservice.repository.AccountRepository;
import com.harsh.accountsservice.service.AccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Component
public class ReportGenerator {
    private final EmailConfig emailConfig;
    private final AccountRepository repository;


    public void sendMessage(SimpleMailMessage mailMessage){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        // Send mail
        mailSender.send(mailMessage);
    }
    // Create an email instance
    public void generateInvoice(Transaction transaction){
        String accountNumber = transaction.getAccountNumber();
        BigDecimal amount = transaction.getTransactionAmount();
        Date date = transaction.getTransactionDateTime();
        Account account = repository.findByAccountNumberEquals(accountNumber).get();
        String email = account.getEmail();

        String date1 = date.toString();


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@agriculturalCropSystem.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Payment Information");
        mailMessage.setText(setBodyForInvoice(amount));

        sendMessage(mailMessage);
    }

    public String setBodyForInvoice(BigDecimal amount){
        String result = "-------------------- *** INVOICE *** --------------------\n" +
                "Agriculture Crop System\t111111,India\n" +
                "The amount paid is "+amount;
        return result;
    }

    public void generateReceipt(Transaction transaction){
        String accountNumber = transaction.getAccountNumber();
        BigDecimal amount = transaction.getTransactionAmount();
        Date date = transaction.getTransactionDateTime();
        Account account = repository.findByAccountNumberEquals(accountNumber).get();
        String email = account.getEmail();

        String date1 = date.toString();


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("admin@agriculturalCropSystem.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("Payment Information");
        mailMessage.setText(setBodyForReceipt(amount));

        sendMessage(mailMessage);
    }
    public String setBodyForReceipt(BigDecimal amount){
        String result = "-------------------- *** PAYMENT RECEIPT *** --------------------\n" +
                "Agriculture Crop System\t111111,India\n" +
                "The amount received is "+amount;
        return result;
    }

}
