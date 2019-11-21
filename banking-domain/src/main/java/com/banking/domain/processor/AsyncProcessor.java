package com.banking.domain.processor;


import com.banking.common.config.KafkaConstants;
import com.banking.common.request.BalanceRequest;
import com.banking.common.request.PageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.BalanceResponse;
import com.banking.common.response.TransactionListResponse;
import com.banking.domain.config.KafkaChannel;
import com.banking.domain.service.AccountService;
import com.banking.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import static com.banking.common.config.KafkaConstants.REQUEST_CHANNEL;
import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Service
@RequiredArgsConstructor
public class AsyncProcessor {

    private final TransactionService transactionService;

    private final AccountService accountService;

    private final KafkaChannel channel;

    @StreamListener(target = REQUEST_CHANNEL,
            condition = "headers['" + KafkaConstants.MEDIA_TYPE_HEADER + "']=='" + PageRequest.MEDIA_TYPE + "'")
    public void processPageRequest(Message<PageRequest> request) {
        channel.reply().send(withPayload(transactionService.listView(request.getPayload()))
                .copyHeaders(request.getHeaders())
                .setHeader(KafkaConstants.MEDIA_TYPE_HEADER, TransactionListResponse.MEDIA_TYPE)
                .build());
    }

    @StreamListener(target = REQUEST_CHANNEL,
            condition = "headers['" + KafkaConstants.MEDIA_TYPE_HEADER + "']=='" + TransactionRequest.MEDIA_TYPE + "'")
    public void processTransactionRequest(Message<TransactionRequest> request) {
        transactionService.create(request.getPayload());
    }

    @StreamListener(target = REQUEST_CHANNEL,
            condition = "headers['" + KafkaConstants.MEDIA_TYPE_HEADER + "']=='" + BalanceRequest.MEDIA_TYPE + "'")
    public void processBalanceRequest(Message<BalanceRequest> request) {
        channel.reply().send(withPayload(accountService.balance(request.getPayload()))
                .copyHeaders(request.getHeaders())
                .setHeader(KafkaConstants.MEDIA_TYPE_HEADER, BalanceResponse.MEDIA_TYPE)
                .build());
    }
}
