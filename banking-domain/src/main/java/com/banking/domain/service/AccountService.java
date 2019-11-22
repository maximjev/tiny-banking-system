package com.banking.domain.service;

import com.banking.common.request.AccountPageRequest;
import com.banking.common.request.BalanceRequest;
import com.banking.common.response.AccountPageResponse;
import com.banking.common.response.BalanceResponse;
import com.banking.domain.mapper.AccountMapper;
import com.banking.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    private final AccountMapper mapper;

    public Optional<BalanceResponse> balance(BalanceRequest request) {
        return repository.findById(request.getAccNumber())
                .map(mapper::balance);
    }

    public AccountPageResponse listView(AccountPageRequest request) {
        return repository.listView(request);
    }
}
