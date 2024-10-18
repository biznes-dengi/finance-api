package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.response.BalanceResponse;
import com.maksyank.finance.saving.domain.BoardSaving;
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
