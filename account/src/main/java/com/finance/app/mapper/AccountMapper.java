package com.finance.app.mapper;

import com.finance.app.boundary.request.RegisterRequest;
import com.finance.app.domain.Account;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "password", expression = "java(encoder.encode(request.pass()))")
    Account accountRequestToAccount(RegisterRequest request, final PasswordEncoder encoder);
}
