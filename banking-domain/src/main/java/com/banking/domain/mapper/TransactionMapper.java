package com.banking.domain.mapper;

import com.banking.common.view.TransactionListView;
import com.banking.domain.config.BaseMapperConfig;
import com.banking.domain.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface TransactionMapper {
    TransactionListView listView(Transaction transaction);
}
