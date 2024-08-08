package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.domain.ImageSaving;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.dto.SavingDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

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

//    @Mapping(target = "image", expression = "java(source.getImage().getValue())")
    @Mapping(source = "balance", target = "amount")
    SavingResponse savingToSavingResponse(Saving source);

    @Mapping(source = "balance", target = "amount")
    List<SavingViewResponse> savingListToSavingViewResponseList(List<Saving> source);

    @Mapping(source = "balance", target = "amount")
    SavingViewResponse savingToSavingViewResponse(Saving source);

    SavingDto savingRequestToSavingDto(SavingRequest source);

    @Mapping(target = "image", expression = "java(new ImageSaving(source.imageType(), source.image()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "lastChange", ignore = true)
    @Mapping(target = "userAccount", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    Saving updateSavingDtoToSaving(SavingDto source, @MappingTarget Saving target);

    default String mapImage(ImageSaving image) {
        return image != null ? image.getValue() : null;
    }
}
