package com.banking.rest.controller;

import com.banking.common.request.TransactionPageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionPageResponse;
import com.banking.rest.facade.TransactionControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.unprocessableEntity;


@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionControllerService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TransactionRequest form) {
        service.create(form);
        return ok().build();
    }

    @GetMapping
    public ResponseEntity<TransactionPageResponse> listView(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return service.listView(new TransactionPageRequest(page, size))
                .map(ResponseEntity::ok)
                .orElseGet(() -> unprocessableEntity().build());
    }
}
