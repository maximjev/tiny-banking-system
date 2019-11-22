package com.banking.rest.facade;

import com.banking.common.request.AccountPageRequest;
import com.banking.common.request.BalanceRequest;
import com.banking.common.response.AccountPageResponse;
import com.banking.common.response.BalanceResponse;
import com.banking.rest.async.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AccountControllerService {

    private final GatewayService gateway;

    public Optional<AccountPageResponse> listView(AccountPageRequest request) {
        return ofNullable(gateway.process(request));
    }

    public Optional<BalanceResponse> balance(BalanceRequest request) {
        return ofNullable(gateway.process(request));
    }
}
