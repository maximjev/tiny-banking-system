package com.banking.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Getter
@Builder
public class Account {

    @Id
    private String number;

    private String client;

    private BigDecimal balance;

    private String currency;

    public void subtract(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
    public void add(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}
