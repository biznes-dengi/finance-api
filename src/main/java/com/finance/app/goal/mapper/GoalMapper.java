package com.finance.app.goal.mapper;

import com.finance.app.goal.boundary.request.GoalRequest;
import com.finance.app.goal.boundary.response.GoalResponse;
import com.finance.app.goal.boundary.response.GoalViewResponse;
import com.finance.app.goal.domain.ImageGoal;
import com.finance.app.goal.domain.Goal;
import com.finance.app.goal.domain.dto.ImageGoalDto;
import com.finance.app.goal.domain.dto.GoalDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        imports = {
                ImageGoal.class
        }
)
public interface GoalMapper {
    @Mapping(source = "title", target = "name")
    @Mapping(source = "goalBalance", target = "balance.amount")
    @Mapping(source = "currency", target = "balance.currency")
    GoalResponse goalToGoalResponse(Goal source);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "goalBalance", target = "balance.amount")
    @Mapping(source = "currency", target = "balance.currency")
    List<GoalViewResponse> goalListToGoalViewResponseList(List<Goal> source);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "goalBalance", target = "balance.amount")
    @Mapping(source = "currency", target = "balance.currency")
    GoalViewResponse goalToGoalViewResponse(Goal source);

    @Mapping(source = "name", target = "title")
    GoalDto goalRequestToGoalDto(GoalRequest source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "goalBalance", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastChange", ignore = true)
    @Mapping(target = "boardGoal", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    Goal updateGoalDtoToGoal(GoalDto source, @MappingTarget Goal target);

    default String mapImage(ImageGoal image) {
        return image.getValue();
    }

    default ImageGoal mapImageDto(ImageGoalDto source) {
        return new ImageGoal(source.imageType(), source.value());
    }
}
