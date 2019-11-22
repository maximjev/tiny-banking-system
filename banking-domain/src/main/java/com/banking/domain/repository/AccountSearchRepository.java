package com.banking.domain.repository;

import com.banking.common.request.AccountPageRequest;
import com.banking.common.response.AccountPageResponse;

public interface AccountSearchRepository {
    AccountPageResponse listView(AccountPageRequest request);
}
