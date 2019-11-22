package com.banking.domain;

import com.banking.domain.config.KafkaChannel;
import com.banking.domain.entity.Account;
import com.banking.domain.entity.ExchangeValue;
import com.banking.domain.repository.AccountRepository;
import com.banking.domain.repository.ExchangeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
@EnableMongoAuditing
@EnableBinding(KafkaChannel.class)
public class DomainApplication implements ApplicationRunner {

    private final AccountRepository accountRepository;

    private final ExchangeValueRepository exchangeValueRepository;

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        accountRepository.deleteAll();
        exchangeValueRepository.deleteAll();
        initAccounts();
        initExchangeValues();
    }

    private void initAccounts() {
        List<Account> accounts = new ArrayList<>();

        accounts.add(Account.builder()
                .client("Jonas Jonaitis")
                .balance(new BigDecimal("120.66"))
                .number("LT12 1000 0111 0100 1000")
                .currency("EUR")
                .build());
        accounts.add(Account.builder()
                .client("Petras Petraitis")
                .balance(new BigDecimal("5000.97"))
                .number("LT12 1000 0111 0100 1001")
                .currency("EUR")
                .build());
        accounts.add(Account.builder()
                .client("Dominykas Domeikaitis")
                .balance(new BigDecimal("10.58"))
                .number("LT12 1000 0111 0100 1002")
                .currency("USD")
                .build());
        accounts.add(Account.builder()
                .client("Nojus Nojutis")
                .balance(new BigDecimal("231.43"))
                .number("LT12 1000 0111 0100 1003")
                .currency("USD")
                .build());

        accounts.add(Account.builder()
                .client("Lukas Laukaitis")
                .balance(new BigDecimal("0.97"))
                .number("LT12 1000 0111 0100 1004")
                .currency("GBP")
                .build());

        accounts.add(Account.builder()
                .client("Benas Benaitis")
                .balance(new BigDecimal("873.22"))
                .number("LT12 1000 0111 0100 1005")
                .currency("GBP")
                .build());

        accountRepository.saveAll(accounts);
    }

    private void initExchangeValues() {
        List<ExchangeValue> values = new ArrayList<>();
        values.add(ExchangeValue.builder()
                .from("EUR")
                .to("USD")
                .conversionMultiplier(new BigDecimal("1.11")).build());
        values.add(ExchangeValue.builder()
                .from("USD")
                .to("EUR")
                .conversionMultiplier(new BigDecimal("0.90")).build());

        values.add(ExchangeValue.builder()
                .from("EUR")
                .to("GBP")
                .conversionMultiplier(new BigDecimal("0.85")).build());

        values.add(ExchangeValue.builder()
                .from("GBP")
                .to("EUR")
                .conversionMultiplier(new BigDecimal("1.16")).build());

        values.add(ExchangeValue.builder()
                .from("USD")
                .to("GBP")
                .conversionMultiplier(new BigDecimal("0.77")).build());

        values.add(ExchangeValue.builder()
                .from("GBP")
                .to("USD")
                .conversionMultiplier(new BigDecimal("1.29")).build());

        exchangeValueRepository.saveAll(values);
    }

}
