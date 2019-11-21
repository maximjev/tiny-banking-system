package com.banking.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TransactionRequest implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.transactionRequest+json";

    @NotBlank
    private String from;

    @NotBlank
    private String to;

    @Positive
    private BigDecimal amount;

    @NotBlank
    private String currency;
}
