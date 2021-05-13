package com.harsh.accountsservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.BSONTimestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.schema.IdentifiableJsonSchemaProperty;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {

    @Id
    private ObjectId _id;

    private String accountNumber;

    private BigDecimal transactionAmount;

    private Date transactionDateTime;

    private String action;

    public Transaction(String accountNumber, BigDecimal transactionAmount, String action,Date transactionDateTime) {
        this.accountNumber = accountNumber;
        this.transactionAmount = transactionAmount;
        this.action = action;
        this.transactionDateTime = transactionDateTime;
    }

    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

}
