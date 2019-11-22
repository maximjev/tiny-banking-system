package com.banking.domain.repository

import com.banking.common.request.TransactionPageRequest
import com.banking.domain.entity.Transaction
import com.banking.domain.repository.TransactionRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import javax.annotation.Resource

import static com.banking.domain.entity.Transaction.builder

@SpringBootTest
@ActiveProfiles("test")
class TransactionRepositoryTest extends Specification {

    @Resource
    MongoOperations operations

    @Resource
    TransactionRepository repository

    def cleanup() {
        operations.remove(new Query(), Transaction)
    }

    def transaction() {
        return builder()
                .from("123")
                .to("456")
                .amount(new BigDecimal("50.00"))
                .convertedAmount(new BigDecimal("50.00"))
                .fromCurrency("EUR")
                .toCurrency("EUR")
                .build();
    }

    def "should create new transaction"() {
        setup:
        def transaction = transaction()

        when:
        def id = repository.save(transaction).id

        then:
        def loaded = operations.findById(id, Transaction)
        loaded.from == transaction.from
        loaded.to == transaction.to
        loaded.convertedAmount == transaction.convertedAmount
        loaded.amount == transaction.amount
        loaded.fromCurrency == transaction.fromCurrency
        loaded.toCurrency == transaction.toCurrency
    }

    def "should load transaction list view"() {
        setup:
        def transaction = transaction()
        operations.insert(transaction)

        when:
        def result = repository.listView(new TransactionPageRequest(0, 5))
        def loaded = result.items[0]
        then:
        result.total == 1
        loaded.from == transaction.from
        loaded.to == transaction.to
        loaded.convertedAmount == transaction.convertedAmount
        loaded.amount == transaction.amount
        loaded.fromCurrency == transaction.fromCurrency
        loaded.toCurrency == transaction.toCurrency
    }

}
