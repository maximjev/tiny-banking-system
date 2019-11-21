package com.banking.domain.mapper;

import com.banking.common.response.BalanceResponse;
import com.banking.domain.config.BaseMapperConfig;
import com.banking.domain.entity.Account;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface AccountMapper {

    BalanceResponse balance(Account account);
}
