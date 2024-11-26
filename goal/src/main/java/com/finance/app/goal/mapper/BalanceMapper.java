package com.finance.app.goal.mapper;

import com.finance.app.goal.boundary.response.BalanceResponse;
import com.finance.app.goal.domain.BoardGoal;
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
