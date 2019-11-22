package com.banking.common.view;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Getter
@Setter
public class AccountListView implements Serializable {
    private String number;
    private String client;
    private BigDecimal balance;
    private String currency;
}
