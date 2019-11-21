package com.banking.domain;

import com.banking.domain.config.KafkaChannel;
import com.banking.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.math.BigDecimal;

import static com.banking.domain.entity.Account.builder;

@SpringBootApplication
@RequiredArgsConstructor
@EnableMongoAuditing
@EnableBinding(KafkaChannel.class)
public class DomainApplication implements ApplicationRunner {

    private final AccountRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        repository.deleteAll();

        //mock accounts
        this.repository.save(builder()
                .client("Jonas Jonaitis")
                .balance(new BigDecimal("120.66"))
                .number("LT12 1000 0111 0100 1000")
                .currency("$")
                .build());
        this.repository.save(builder()
                .client("Petras Petraitis")
                .balance(new BigDecimal("5000.97"))
                .number("LT12 1000 0111 0100 1001")
                .currency("$")
                .build());
        this.repository.save(builder()
                .client("Jonas Jonaitis")
                .balance(new BigDecimal("10.58"))
                .number("LT12 1000 0111 0100 1002")
                .currency("$")
                .build());
        this.repository.save(builder()
                .client("Jonas Jonaitis")
                .balance(new BigDecimal("458.32"))
                .number("LT12 1000 0111 0100 1003")
                .currency("$")
                .build());
    }

}
