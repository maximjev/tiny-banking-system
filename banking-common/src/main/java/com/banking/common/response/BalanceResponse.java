package com.banking.common.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.balanceResponse+json";

    private BigDecimal balance;
    private String currency;
}
