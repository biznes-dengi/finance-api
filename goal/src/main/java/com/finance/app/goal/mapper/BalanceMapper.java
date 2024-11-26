package com.finance.app.mapper;

import com.finance.app.boundary.response.BalanceResponse;
import com.finance.app.domain.BoardGoal;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BalanceMapper {

    @Mapping(source = "boardBalance", target = "amount")
    BalanceResponse boardGoalToBalanceResponse(BoardGoal source);
}
