package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.domain.Saving;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface StateOfSavingResponseMapper {

    StateOfSavingResponse savingToStateOfSavingResponse(Saving source);
}
