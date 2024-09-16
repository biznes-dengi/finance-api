package com.maksyank.finance.account.mapper;

import com.maksyank.finance.account.boundary.AccountRequest;
import com.maksyank.finance.account.domain.Account;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(source = "pass", target = "password")
    Account accountRequestToAccount(AccountRequest request);
}
