package com.banking.domain.repository;

import com.banking.common.request.AccountPageRequest;
import com.banking.common.response.AccountPageResponse;
import com.banking.domain.entity.Account;
import com.banking.domain.mapper.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Query.query;

@AllArgsConstructor
@Repository
public class AccountSearchRepositoryImpl implements AccountSearchRepository {

    private final MongoOperations operations;

    private final AccountMapper mapper;

    @Override
    public AccountPageResponse listView(AccountPageRequest page) {
        var conditions = new Criteria();

        var total = operations.count(query(conditions), Account.class);

        var entities = operations.find(
                query(conditions)
                        .skip(page.getSize() * page.getPage())
                        .limit(page.getSize()),
                Account.class)
                .stream()
                .map(mapper::listView)
                .collect(toList());

        return new AccountPageResponse(entities, page.getPage(), page.getSize(), total);
    }
}
