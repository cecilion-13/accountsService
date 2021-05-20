package com.harsh.accountsservice.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "account-service-demo1")
public class Account {

    @Id
    private ObjectId _id;

    @Size(min = 14,max = 14)
    @Digits(fraction = 0, integer = 14)
    private String accountNumber;

    @Size(min = 11,max = 11)
    private String ifscCode;

    @NotNull
    @PositiveOrZero
    private BigDecimal currentBalance;

    private String email;

    public Account(String accountNumber, String ifscCode, BigDecimal currentBalance, String email) {
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.currentBalance = currentBalance;
        this.email = email;
    }

    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

}
