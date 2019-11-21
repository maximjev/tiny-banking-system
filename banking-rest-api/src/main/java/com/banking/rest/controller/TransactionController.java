package com.banking.rest.controller;

import com.banking.common.request.PageRequest;
import com.banking.common.request.TransactionRequest;
import com.banking.common.response.TransactionListResponse;
import com.banking.rest.facade.TransactionControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;


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
    public ResponseEntity<TransactionListResponse> listView(
            @RequestParam("page") int pageIndex,
            @RequestParam("size") int pageSize) {
        return ok(service.listView(new PageRequest(pageIndex, pageSize)));
    }
}
