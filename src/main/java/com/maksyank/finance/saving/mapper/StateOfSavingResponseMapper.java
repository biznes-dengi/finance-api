package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.domain.Saving;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface StateOfSavingResponseMapper {
    @Mapping(source = "savingBalance", target = "balance")
    StateOfSavingResponse savingToStateOfSavingResponse(Saving source);
}
