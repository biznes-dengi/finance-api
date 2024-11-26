package com.finance.app.goal.mapper;

import com.finance.app.goal.boundary.response.BoardGoalResponse;
import com.finance.app.goal.domain.BoardGoal;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BoardGoalMapper {

    @Mapping(source = "id", target = "boardGoalId")
    BoardGoalResponse boardGoalToBoardGoalResponse(BoardGoal source);
}
