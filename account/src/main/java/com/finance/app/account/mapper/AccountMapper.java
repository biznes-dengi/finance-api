package com.finance.app.account.mapper;

import com.finance.app.account.boundary.request.AccountRequest;
import com.finance.app.account.boundary.response.AccountResponse;
import com.finance.app.account.domain.Account;
import com.finance.app.account.domain.dto.AccountDto;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AccountMapper {

    AccountResponse accountToAccountResponse(Account source);

    AccountDto accountRequestToAccountDto(AccountRequest source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    Account accountDtoToAccount(AccountDto source);
}
