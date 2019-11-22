package com.banking.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPageRequest implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.transactionPageRequest+json";

    private int page;
    private int size;
}
