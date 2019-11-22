package com.banking.domain.domain

import com.banking.domain.domain.CurrencyManager
import com.banking.domain.domain.exception.ExchangeException
import com.banking.domain.entity.ExchangeValue
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.annotation.Resource

@SpringBootTest
@ActiveProfiles("test")
class CurrencyManagerTest extends Specification {

    @Resource
    MongoOperations operations

    @Resource
    CurrencyManager manager

    def setup() {
        operations.remove(new Query(), ExchangeValue)
    }

    def cleanup() {
        operations.remove(new Query(), ExchangeValue)
    }

    def "should exchange currency"() {
        setup:
        def value = ExchangeValue.builder()
                .from("USD")
                .to("EUR")
                .conversionMultiplier(new BigDecimal("0.90"))
                .build()
        operations.save(value)

        when:
        def res = manager.exchange(value.from, value.to, new BigDecimal("1.00"))

        then:
        res == new BigDecimal("0.90")
    }

    def "should throw exchange exception on destination currency" () {
        setup:
        def value = ExchangeValue.builder()
                .from("USD")
                .to("EUR")
                .conversionMultiplier(new BigDecimal("0.90"))
                .build()
        operations.save(value)

        when:
        manager.exchange(value.from, "GBP", new BigDecimal("1.00"))

        then:
        ExchangeException ex = thrown()
        ex.getMessage() == "Exchange rate for USD to GBP not found."
    }

    def "should throw exchange exception on source currency" () {
        setup:
        def value = ExchangeValue.builder()
                .from("USD")
                .to("EUR")
                .conversionMultiplier(new BigDecimal("0.90"))
                .build()
        operations.save(value)

        when:
        manager.exchange("GBP", value.to, new BigDecimal("1.00"))

        then:
        ExchangeException ex = thrown()
        ex.getMessage() == "Exchange rate for GBP to EUR not found."
    }

}
