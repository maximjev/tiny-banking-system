package com.banking.common.request;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BalanceRequest implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.balanceRequest+json";

    private String accNumber;
}
