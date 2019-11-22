package com.banking.domain.service;

import com.banking.common.request.TransactionPageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionPageResponse;
import com.banking.domain.domain.TransactionManager;
import com.banking.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository repository;

    private final TransactionManager manager;

    public void create(TransactionRequest form) {
        try {
            manager.processTransaction(form);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public TransactionPageResponse listView(TransactionPageRequest page) {
        return repository.listView(page);
    }
}
