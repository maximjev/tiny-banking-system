package com.banking.domain.service;

import com.banking.common.request.PageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionListResponse;
import com.banking.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public void create(TransactionRequest form) {
        repository.save(form);
    }

    public TransactionListResponse listView(PageRequest page) {
        return repository.listView(page);
    }
}
