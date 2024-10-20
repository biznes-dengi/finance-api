package com.finance.app.mapper;

import com.finance.app.boundary.response.BalanceResponse;
import com.finance.app.domain.BoardSaving;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BalanceMapper {

    @Mapping(source = "boardBalance", target = "amount")
    BalanceResponse boardSavingToBalanceResponse(BoardSaving source);
}
