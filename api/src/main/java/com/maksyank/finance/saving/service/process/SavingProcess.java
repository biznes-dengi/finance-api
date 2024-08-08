package com.maksyank.finance.saving.service.process;

import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.boundary.response.SavingViewResponse;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.businessrules.InitRulesSaving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.dto.SavingDto;
import com.maksyank.finance.saving.exception.DbOperationException;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.SavingMapper;
import com.maksyank.finance.saving.service.persistence.TransactionPersistence;
import com.maksyank.finance.saving.service.persistence.SavingPersistence;
import com.maksyank.finance.saving.service.validation.SavingValidationService;
import com.maksyank.finance.user.domain.UserAccount;
import com.maksyank.finance.user.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@EnableAsync
public class SavingProcess {
    private SavingPersistence savingPersistence;
    private TransactionPersistence transactionPersistence;
    private SavingValidationService savingValidationService;
    private UserAccountService userAccountService;
    @Autowired
    SavingProcess(
            UserAccountService userAccountService,
            SavingPersistence savingPersistence,
            TransactionPersistence transactionPersistence,
            SavingValidationService savingValidationService
    ) {
        this.userAccountService = userAccountService;
        this.savingPersistence = savingPersistence;
        this.transactionPersistence = transactionPersistence;
        this.savingValidationService = savingValidationService;
    }

    public SavingResponse processGetById(int id, int userId) throws NotFoundException {
        final var foundSaving = this.savingPersistence.findByIdAndUserId(id, userId);
        return SavingMapper.entityToResponse(foundSaving);
    }

    public List<SavingViewResponse> processGetByState(SavingState state, int userId) throws NotFoundException {
        final var foundSavings = this.savingPersistence.findByStateAndUserId(state, userId);
        return SavingMapper.sourceToViewResponse(foundSavings);
    }

    public void processSave(SavingDto savingDto, UserAccount user) throws DbOperationException, ValidationException {
        final var resultOfValidation = this.savingValidationService.validate(savingDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var rulesSaving = new InitRulesSaving(SavingState.ACTIVE, BigDecimal.ZERO);
        final var newSaving = SavingMapper.mapToNewEntity(savingDto, rulesSaving, user);
        this.savingPersistence.save(newSaving);
    }

    public void processUpdate(int id, SavingDto finGoalToSaveDto, UserAccount user) throws NotFoundException, DbOperationException, ValidationException {
        final var resultOfValidation = this.savingValidationService.validate(finGoalToSaveDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var oldSaving = this.savingPersistence.findByIdAndUserId(id, user.getId());
        final var updatedSaving = SavingMapper.mapToEntity(finGoalToSaveDto, oldSaving);
        this.savingPersistence.save(updatedSaving);
    }

    public void processDelete(int id) throws DbOperationException {
        this.transactionPersistence.removeAllBySavingId(id);
        this.savingPersistence.deleteById(id);
    }

    public Saving updateBalance(BigDecimal amountNewDeposit, int financeGoalId, int userId) throws NotFoundException, DbOperationException {
        final var savingForUpdateBalance = this.savingPersistence.findByIdAndUserId(financeGoalId, userId);
        final var newBalance = savingForUpdateBalance.getBalance().add(amountNewDeposit);
        savingForUpdateBalance.setBalance(newBalance);

        this.updateState(savingForUpdateBalance);
        this.savingPersistence.save(savingForUpdateBalance);
        
        return savingForUpdateBalance;
    }

    private void updateState(Saving saving) {
        if (saving.getState() == SavingState.OVERDUE || saving.getTargetAmount() == null)
            return;

        if (saving.getBalance().compareTo(saving.getTargetAmount()) >= 0) {
            saving.setState(SavingState.ACHIEVED);
        } else {
            saving.setState(SavingState.ACTIVE);
        }
    }

    // TODO it's temporary impl, task in Notion
    // TODO there's bug with time zone, right now the impl only for one time zone
    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void scheduledCheckSavingsIfOverdue() {
        this.userAccountService.getListIdsOfUsers().stream()
                .map(userId -> this.savingPersistence.findByUserIdAndStateAndIfDeadlineIsNotNull(SavingState.ACTIVE, userId))
                .filter(listSaving -> !listSaving.isEmpty())
                .forEach(listSaving ->
                    listSaving.stream()
                            .filter(saving -> saving.getDeadline().isBefore(LocalDate.now()) ||
                                    saving.getDeadline().isEqual(LocalDate.now()))
                            .forEach(saving -> {
                                saving.setState(SavingState.OVERDUE);
                                try {
                                    this.savingPersistence.save(saving);
                                } catch (DbOperationException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                );
    }
}
