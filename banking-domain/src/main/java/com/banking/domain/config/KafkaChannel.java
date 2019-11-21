package com.banking.domain.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import static com.banking.common.config.KafkaConstants.REPLY_CHANNEL;
import static com.banking.common.config.KafkaConstants.REQUEST_CHANNEL;

public interface KafkaChannel {
    @Output(REPLY_CHANNEL)
    MessageChannel reply();

    @Input(REQUEST_CHANNEL)
    SubscribableChannel request();
}
