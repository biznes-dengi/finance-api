package com.finance.app.mapper;

import com.finance.app.boundary.response.BoardGoalResponse;
import com.finance.app.domain.BoardGoal;
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
