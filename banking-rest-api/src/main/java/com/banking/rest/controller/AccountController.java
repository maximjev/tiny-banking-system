package com.banking.rest.controller;

import com.banking.common.request.AccountPageRequest;
import com.banking.common.request.BalanceRequest;
import com.banking.common.response.BalanceResponse;
import com.banking.rest.facade.AccountControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.unprocessableEntity;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountControllerService service;

    @GetMapping
    public ResponseEntity<?> listView(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return service.listView(new AccountPageRequest(page, size))
                .map(ResponseEntity::ok)
                .orElseGet(() -> unprocessableEntity().build());
    }

    @GetMapping("/{number}")
    public ResponseEntity<BalanceResponse> balance(@PathVariable String number) {
        return service.balance(new BalanceRequest(number))
                .map(ResponseEntity::ok)
                .orElseGet(() -> unprocessableEntity().build());
    }
}
