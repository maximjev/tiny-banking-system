package com.banking.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequest implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.balanceRequest+json";

    private String accNumber;
}
