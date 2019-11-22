package com.banking.rest.facade;

import com.banking.common.request.TransactionPageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionPageResponse;
import com.banking.rest.async.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TransactionControllerService {

    private final GatewayService gateway;

    public void create(TransactionRequest form) {
       gateway.process(form);
    }

    public Optional<TransactionPageResponse> listView(TransactionPageRequest page) {
        return ofNullable(gateway.process(page));
    }
}
