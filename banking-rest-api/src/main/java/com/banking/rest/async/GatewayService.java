package com.banking.rest.async;

import com.banking.common.request.AccountPageRequest;
import com.banking.common.request.BalanceRequest;
import com.banking.common.request.TransactionPageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.AccountPageResponse;
import com.banking.common.response.BalanceResponse;
import com.banking.common.response.TransactionPageResponse;
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
                    value = TransactionPageRequest.MEDIA_TYPE)
            })
    TransactionPageResponse process(TransactionPageRequest page);

    @Gateway(requestChannel = ENRICH_REQUEST_CHANNEL,
            requestTimeout = 1000,
            headers = {@GatewayHeader(
                    name = MEDIA_TYPE_HEADER,
                    value = TransactionRequest.MEDIA_TYPE)
            })
    void process(TransactionRequest request);

    @Gateway(requestChannel = ENRICH_REQUEST_CHANNEL,
            replyChannel = FILTER_REPLY_CHANNEL,
            replyTimeout = 1000,
            requestTimeout = 1000,
            headers = {@GatewayHeader(
                    name = MEDIA_TYPE_HEADER,
                    value = AccountPageRequest.MEDIA_TYPE)
            })
    AccountPageResponse process(AccountPageRequest request);

    @Gateway(requestChannel = ENRICH_REQUEST_CHANNEL,
            replyChannel = FILTER_REPLY_CHANNEL,
            replyTimeout = 1000,
            requestTimeout = 1000,
            headers = {@GatewayHeader(
                    name = MEDIA_TYPE_HEADER,
                    value = BalanceRequest.MEDIA_TYPE)
            })
    BalanceResponse process(BalanceRequest request);
}
