package com.banking.domain.repository;

import com.banking.domain.entity.ExchangeValue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExchangeValueRepository extends MongoRepository<ExchangeValue, String> {
    Optional<ExchangeValue> findFirstByFromAndTo(String from, String to);
}
