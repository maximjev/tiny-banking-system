package com.banking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExchangeValue {
    @Id
    private String id;

    private String from;
    private String to;
    private BigDecimal conversionMultiplier;
}
