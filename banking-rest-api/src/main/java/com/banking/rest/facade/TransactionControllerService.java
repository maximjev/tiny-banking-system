package com.banking.rest.facade;

import com.banking.common.request.PageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionListResponse;
import com.banking.rest.async.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionControllerService {

    private final GatewayService gateway;

    public void create(TransactionRequest form) {
       gateway.process(form);
    }

    public TransactionListResponse listView(PageRequest page) {
        return gateway.process(page);
    }
}
