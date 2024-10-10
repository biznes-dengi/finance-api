package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.domain.ImageSaving;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.dto.ImageSavingDto;
import com.maksyank.finance.saving.domain.dto.SavingDto;
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
                ImageSaving.class
        }
)
public interface SavingMapper {
    @Mapping(source = "title", target = "name")
    @Mapping(source = "savingBalance", target = "balance.amount")
    @Mapping(source = "currency", target = "balance.currency")
    SavingResponse savingToSavingResponse(Saving source);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "savingBalance", target = "balance.amount")
    @Mapping(source = "currency", target = "balance.currency")
    List<SavingViewResponse> savingListToSavingViewResponseList(List<Saving> source);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "savingBalance", target = "balance.amount")
    @Mapping(source = "currency", target = "balance.currency")
    SavingViewResponse savingToSavingViewResponse(Saving source);

    @Mapping(source = "name", target = "title")
    SavingDto savingRequestToSavingDto(SavingRequest source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "savingBalance", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastChange", ignore = true)
    @Mapping(target = "boardSaving", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    Saving updateSavingDtoToSaving(SavingDto source, @MappingTarget Saving target);

    default String mapImage(ImageSaving image) {
        return image.getValue();
    }

    default ImageSaving mapImageDto(ImageSavingDto source) {
        return new ImageSaving(source.imageType(), source.value());
    }
}
