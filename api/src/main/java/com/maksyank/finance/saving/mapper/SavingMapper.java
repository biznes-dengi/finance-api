package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.response.StateOfSavingResponse;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.domain.ImageSaving;
import com.maksyank.finance.saving.domain.businessrules.InitRulesSaving;
import com.maksyank.finance.saving.dto.SavingDto;
import com.maksyank.finance.user.domain.UserAccount;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SavingMapper {

    public static List<SavingResponse> entityToResponse(Collection<Saving> finGoals) {
        return finGoals.stream().map(SavingMapper::entityToResponse).collect(Collectors.toList());
    }

    public static SavingResponse entityToResponse(Saving source) {
        return new SavingResponse(source.getId(), source.getTitle(), source.getState(),
                source.getDescription(), source.getBalance(), source.getTargetAmount(),
                source.getDeadline(), source.getRiskProfile(), source.getImage().getValue()
        );
    }

    public static List<SavingViewResponse> sourceToViewResponse(Collection<Saving> finGoals) {
        return finGoals.stream().map(SavingMapper::sourceToViewResponse).toList();
    }

    public static SavingViewResponse sourceToViewResponse(Saving source) {
        return new SavingViewResponse(source.getId(), source.getTitle(),
                source.getBalance(), source.getTargetAmount(), source.getImage().getValue()
        );
    }

    public static SavingDto mapToDto(SavingRequest source) {
        return new SavingDto(
                source.title(), source.currency(), source.description(), source.targetAmount(),
                source.deadline(), source.riskProfile(), source.image(), source.imageType()
        );
    }

    public static Saving mapToNewEntity(
            SavingDto source,
            InitRulesSaving rulesFinanceGoal,
            UserAccount user
    ) {
        return new Saving(
                rulesFinanceGoal, source.title(), source.currency(), source.description(), source.targetAmount(),
                source.deadline(), source.riskProfile(), new ImageSaving(source.imageType(), source.image()), user
        );
    }

    public static Saving mapToEntity(SavingDto source, Saving destination) {
        destination.setTitle(source.title());
        destination.setCurrency(source.currency());
        destination.setDescription(source.description());
        destination.setTargetAmount(source.targetAmount());
        destination.setDeadline(source.deadline());
        destination.setRiskProfile(source.riskProfile());
        destination.setImage(new ImageSaving(source.imageType(), source.image()));
        return destination;
    }

    public static StateOfSavingResponse mapToStateResponse(Saving source) {
        return new StateOfSavingResponse(source.getBalance(), source.getState());
    }

}
