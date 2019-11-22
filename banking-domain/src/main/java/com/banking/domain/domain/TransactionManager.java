package com.banking.domain.domain;

import com.banking.common.request.TransactionRequest;
import com.banking.domain.domain.exception.ExchangeException;
import com.banking.domain.domain.exception.InvalidTransaction;
import com.banking.domain.entity.Transaction;
import com.banking.domain.repository.AccountRepository;
import com.banking.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.util.List.of;

@Service
@RequiredArgsConstructor
public class TransactionManager {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final CurrencyManager currencyManager;

    public Transaction processTransaction(TransactionRequest request) throws InvalidTransaction, ExchangeException {
        if (invalidAccounts(request)) {
            throw new InvalidTransaction("Invalid account numbers.");
        }
        if(request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransaction("Amount should be positive.");
        }
        var from = accountRepository.findById(request.getFrom()).get();
        var to = accountRepository.findById(request.getTo()).get();

        if (request.getAmount().compareTo(from.getBalance()) > 0) {
            throw new InvalidTransaction("Insufficient funds.");
        }

        BigDecimal convertedAmount;
        if (from.getCurrency().equals(to.getCurrency())) {
            convertedAmount = request.getAmount();
        } else {
            convertedAmount = currencyManager.exchange(
                    from.getCurrency(),
                    to.getCurrency(),
                    request.getAmount()
            );
        }

        from.subtract(request.getAmount());
        to.add(convertedAmount);

        Transaction transaction = Transaction.builder()
                .from(from.getNumber())
                .to(to.getNumber())
                .amount(request.getAmount())
                .convertedAmount(convertedAmount)
                .fromCurrency(from.getCurrency())
                .toCurrency(to.getCurrency())
                .build();

//        Thread.sleep(Duration.ofDays(3).toMillis());
        accountRepository.saveAll(of(to, from));
        return transactionRepository.save(transaction);
    }

    private boolean invalidAccounts(TransactionRequest request) {
        return accountRepository.findById(request.getFrom()).isEmpty() ||
                accountRepository.findById(request.getTo()).isEmpty();
    }
}
