package com.banking.common.response;

import com.banking.common.view.AccountListView;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPageResponse implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.accountPageResponse+json";

    private List<AccountListView> items;
    private int page;
    private int size;
    private long total;
}
