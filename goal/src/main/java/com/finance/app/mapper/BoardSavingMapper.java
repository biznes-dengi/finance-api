package com.finance.app.mapper;

import com.finance.app.boundary.response.BoardSavingResponse;
import com.finance.app.domain.BoardSaving;
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
