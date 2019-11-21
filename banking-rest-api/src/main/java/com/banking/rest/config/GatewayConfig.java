package com.banking.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.IntegrationConverter;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.io.IOException;
import java.util.Set;

import static com.banking.common.config.KafkaConstants.*;
import static com.banking.rest.utils.Constants.INSTANCE_ID;
import static com.banking.rest.utils.Constants.INSTANCE_ID_HEADER;
import static java.util.Collections.singleton;

@Configuration
@EnableIntegration
@Slf4j
public class GatewayConfig {

    @Bean
    public IntegrationFlow requestFlow() {
        return IntegrationFlows.from(ENRICH_REQUEST_CHANNEL)
                .enrichHeaders(HeaderEnricherSpec::headerChannelsToString)
                .enrichHeaders(headerEnricherSpec -> headerEnricherSpec.header(INSTANCE_ID_HEADER, INSTANCE_ID))
                .channel(REQUEST_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow replyFlow() {
        return IntegrationFlows.from(REPLY_CHANNEL)
                .filter(Message.class, message -> INSTANCE_ID.equals(message.getHeaders().get(INSTANCE_ID_HEADER)))
                .channel(FILTER_REPLY_CHANNEL)
                .get();
    }

    @Bean
    @IntegrationConverter
    public GenericConverter genericConverter() {
        return new JsonMessageConverter();
    }

    public class JsonMessageConverter implements GenericConverter {
        private final ObjectMapper MAPPER;

        public JsonMessageConverter() {
            MAPPER = new MappingJackson2MessageConverter().getObjectMapper();
        }

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return singleton(new ConvertiblePair(byte[].class, Object.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            Object result;
            try {
                result = source instanceof String ? MAPPER.readValue((String) source, targetType.getType()) : MAPPER.readValue((byte[]) source, targetType.getType());
            } catch (IOException e) {
                log.debug("Failed to convert");
                result = null;
            }

            return result;
        }
    }
}
