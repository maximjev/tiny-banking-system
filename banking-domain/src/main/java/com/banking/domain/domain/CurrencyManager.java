package com.banking.domain.domain;

import com.banking.domain.domain.exception.ExchangeException;
import com.banking.domain.repository.ExchangeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CurrencyManager {

    private final ExchangeValueRepository repository;

    public BigDecimal exchange(String from, String to, BigDecimal amount) throws ExchangeException {
        return repository.findFirstByFromAndTo(from, to)
                .map(ev -> ev.getConversionMultiplier().multiply(amount))
                .orElseThrow(() -> new ExchangeException(format("Exchange rate for %s to %s not found.", from, to)));
    }
}
