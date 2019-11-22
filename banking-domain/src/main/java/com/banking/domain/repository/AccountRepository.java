package com.banking.domain.repository;

import com.banking.domain.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String>, AccountSearchRepository {
}
