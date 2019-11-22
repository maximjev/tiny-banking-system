package com.banking.domain.domain

import com.banking.common.request.TransactionRequest
import com.banking.domain.domain.CurrencyManager
import com.banking.domain.domain.TransactionManager
import com.banking.domain.domain.exception.InvalidTransaction
import com.banking.domain.entity.Account
import com.banking.domain.entity.ExchangeValue
import com.banking.domain.entity.Transaction
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.annotation.Resource

@SpringBootTest
@ActiveProfiles("test")
class TransactionManagerTest extends Specification {

    @Resource
    MongoOperations operations

    @Resource
    TransactionManager manager

    @Resource
    CurrencyManager currencyManager

    def setup() {
        operations.remove(new Query(), ExchangeValue)
        operations.remove(new Query(), Transaction)
        operations.remove(new Query(), Account)
    }

    def cleanup() {
        operations.remove(new Query(), ExchangeValue)
        operations.remove(new Query(), Transaction)
        operations.remove(new Query(), Account)
    }

    def fromAccount() {
        return Account.builder()
                .number("123")
                .currency("EUR")
                .balance(new BigDecimal("100.00"))
                .client("Jonas Jonaitis")
                .build()
    }

    def toAccount() {
        Account.builder()
                .number("456")
                .currency("EUR")
                .balance(new BigDecimal("100.00"))
                .client("Petras Petraitis")
                .build()
    }

    def "should create same currency transaction"() {
        setup:
        def from = fromAccount()
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("50.00")

        when:
        def id = manager.processTransaction(request).id

        then:
        def transaction = operations.findById(id, Transaction)
        transaction.amount == request.amount
        transaction.convertedAmount == request.amount
        transaction.from == request.from
        transaction.to == request.to
        transaction.fromCurrency == from.currency
        transaction.toCurrency == from.currency
    }

    def "should create different currency transaction"() {
        setup:
        def from = fromAccount()
        from.currency = "USD"
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def currency = ExchangeValue.builder()
                .from("USD")
                .to("EUR")
                .conversionMultiplier(new BigDecimal(2))
                .build()
        operations.save(currency)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("50.00")

        when:
        def id = manager.processTransaction(request).id

        then:
        def transaction = operations.findById(id, Transaction)
        transaction.amount == request.amount
        transaction.convertedAmount == currencyManager.exchange("USD", "EUR", request.amount)
        transaction.from == request.from
        transaction.to == request.to
        transaction.fromCurrency == from.currency
        transaction.toCurrency == to.currency
    }

    def "should readjust account balance same currency"() {
        setup:
        def from = fromAccount()
        from.currency = "USD"
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def currency = ExchangeValue.builder()
                .from("USD")
                .to("EUR")
                .conversionMultiplier(new BigDecimal(2))
                .build()
        operations.save(currency)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("50.00")

        when:
        manager.processTransaction(request)

        then:
        def fromRes = operations.findById(from.number, Account)
        def toRes = operations.findById(to.number, Account)
        fromRes.balance == new BigDecimal("50.00")
        toRes.balance == new BigDecimal("200.00")
    }

    def "should readjust account balance different currency"() {
        setup:
        def from = fromAccount()
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("50.00")

        when:
        manager.processTransaction(request)

        then:
        def fromRes = operations.findById(from.number, Account)
        def toRes = operations.findById(to.number, Account)
        fromRes.balance == new BigDecimal("50.00")
        toRes.balance == new BigDecimal("150.00")
    }

    def "should throw invalid transaction on insufficient balance"() {
        setup:
        def from = fromAccount()
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("100.01")

        when:
        manager.processTransaction(request)

        then:
        InvalidTransaction ex = thrown()
        ex.message == "Insufficient funds."
    }

    def "should throw invalid transaction on not existing sender account"() {
        setup:
        def from = fromAccount()
        from.number = "000"
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("50.00")

        when:
        manager.processTransaction(request)

        then:
        InvalidTransaction ex = thrown()
        ex.message == "Invalid account numbers."
    }

    def "should throw invalid transaction on not existing receiver account"() {
        setup:
        def from = fromAccount()
        operations.save(from)

        def to = toAccount()
        to.number = "000"
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("50.00")

        when:
        manager.processTransaction(request)

        then:
        InvalidTransaction ex = thrown()
        ex.message == "Invalid account numbers."
    }

    def "should throw invalid transaction on negative amount"() {
        setup:
        def from = fromAccount()
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("-50.00")

        when:
        manager.processTransaction(request)

        then:
        InvalidTransaction ex = thrown()
        ex.message == "Amount should be positive."
    }

    def "should throw invalid transaction on zero amount"() {
        setup:
        def from = fromAccount()
        operations.save(from)

        def to = toAccount()
        operations.save(to)

        def request = new TransactionRequest()
        request.from = "123"
        request.to = "456"
        request.amount = new BigDecimal("0")

        when:
        manager.processTransaction(request)

        then:
        InvalidTransaction ex = thrown()
        ex.message == "Amount should be positive."
    }

}
