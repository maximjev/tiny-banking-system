package com.banking.domain.repository;

import com.banking.common.request.TransactionPageRequest;
import com.banking.common.response.TransactionPageResponse;
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

    public Transaction save(Transaction transaction) {
        return operations.save(transaction);
    }

    public TransactionPageResponse listView(TransactionPageRequest page) {
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

        return new TransactionPageResponse(entities, page.getPage(), page.getSize(), total);
    }
}
