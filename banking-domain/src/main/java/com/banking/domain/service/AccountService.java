package com.banking.domain.service;

import com.banking.common.request.BalanceRequest;
import com.banking.common.response.BalanceResponse;
import com.banking.domain.mapper.AccountMapper;
import com.banking.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    public BalanceResponse balance(BalanceRequest request) {
        return repository.findById(request.getAccNumber())
                .map(mapper::balance)
                .orElse(null);
    }
}
