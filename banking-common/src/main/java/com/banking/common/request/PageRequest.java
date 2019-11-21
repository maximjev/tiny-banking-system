package com.banking.common.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PageRequest implements Serializable {
    public static final String MEDIA_TYPE = "application/vnd.pageRequest+json";

    private int page;
    private int size;

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
