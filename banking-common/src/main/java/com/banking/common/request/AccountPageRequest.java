package com.banking.common.request;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountPageRequest implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.accountPageRequest+json";
    private int page;
    private int size;
}
