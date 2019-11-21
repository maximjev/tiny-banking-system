package com.banking.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document
@Builder
@Getter
public class Transaction {

    @Id
    private String id;

    private String from;

    private String to;

    private BigDecimal amount;

    private String currency;

    @CreatedDate
    private LocalDateTime createdAt;
}
