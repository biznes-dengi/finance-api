package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.response.BoardSavingResponse;
import com.maksyank.finance.saving.domain.BoardSaving;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BoardSavingMapper {

    @Mapping(source = "id", target = "boardSavingId")
    BoardSavingResponse boardSavingToBoardSavingResponse(BoardSaving source);
}
