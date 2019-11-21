package com.banking.rest.async;

import com.banking.common.request.PageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionListResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

import static com.banking.common.config.KafkaConstants.*;

@Service
@MessagingGateway
public interface GatewayService {

    @Gateway(requestChannel = ENRICH_REQUEST_CHANNEL,
            replyChannel = FILTER_REPLY_CHANNEL,
            replyTimeout = 1000,
            requestTimeout = 1000,
            headers = {@GatewayHeader(
                    name = MEDIA_TYPE_HEADER,
                    value = PageRequest.MEDIA_TYPE)
            })
    TransactionListResponse process(PageRequest page);

    @Gateway(requestChannel = ENRICH_REQUEST_CHANNEL,
            requestTimeout = 1000,
            headers = {@GatewayHeader(
                    name = MEDIA_TYPE_HEADER,
                    value = TransactionRequest.MEDIA_TYPE)
            })
    void process(TransactionRequest page);
}
