package com.banking.domain.repository;

import com.banking.common.request.PageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionListResponse;
import com.banking.domain.entity.Transaction;
import com.banking.domain.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final MongoOperations operations;

    private final TransactionMapper mapper;

    public void save(TransactionRequest form) {
        var transaction = Transaction.builder()
                .from(form.getFrom())
                .to(form.getTo())
                .amount(form.getAmount())
                .currency(form.getCurrency())
                .build();

        operations.save(transaction);
    }

    public TransactionListResponse listView(PageRequest page) {
        var conditions = new Criteria();

        var total = operations.count(query(conditions), Transaction.class);

        var entities = operations.find(
                query(conditions)
                        .skip(page.getSize() * page.getPage())
                        .limit(page.getSize()),
                Transaction.class)
                .stream()
                .sorted(comparing(Transaction::getCreatedAt, reverseOrder()))
                .map(mapper::listView)
                .collect(toList());

        return new TransactionListResponse(entities, page.getPage(), page.getSize(), total);
    }
}
