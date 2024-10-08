package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.boundary.request.SavingRequest;
import com.maksyank.finance.saving.boundary.response.SavingAllResponse;
import com.maksyank.finance.saving.boundary.response.SavingResponse;
import com.maksyank.finance.saving.dao.BoardSavingDao;
import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.dao.TransactionDao;
import com.maksyank.finance.saving.domain.BoardSaving;
import com.maksyank.finance.saving.domain.ImageSaving;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.businessrules.InitRulesSaving;
import com.maksyank.finance.saving.domain.enums.SavingState;
import com.maksyank.finance.saving.domain.dto.SavingDto;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ValidationException;
import com.maksyank.finance.saving.mapper.SavingMapper;
import com.maksyank.finance.saving.validation.service.SavingValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SavingProcess {
    private final SavingDao savingDao;
    private final BoardSavingDao boardSavingDao;
    private final TransactionDao transactionDao;
    private final SavingValidationService savingValidationService;
    private final SavingMapper savingMapper;

    public SavingResponse processGetById(int savingId, int boardSavingId) throws NotFoundException {
        final var foundSaving = savingDao.fetchSavingById(savingId, boardSavingId);
        return savingMapper.savingToSavingResponse(foundSaving);
    }

    public SavingAllResponse processGetByState(SavingState state, int boardSavingId, int pageNumber) throws NotFoundException {
        final var foundSliceListSaving = savingDao.fetchSavingsByState(state, boardSavingId, pageNumber);
        final var mappedSavingViewResponse =
                savingMapper.savingListToSavingViewResponseList(foundSliceListSaving.getContent());
        return new SavingAllResponse(mappedSavingViewResponse, foundSliceListSaving.hasNext());
    }

    public SavingAllResponse processGetAll(int boardSavingId, int pageNumber) throws NotFoundException {
        final var foundSliceListSaving = savingDao.fetchAllSavings(boardSavingId, pageNumber);
        final var mappedSavingViewResponse =
                savingMapper.savingListToSavingViewResponseList(foundSliceListSaving.getContent());
        return new SavingAllResponse(mappedSavingViewResponse, foundSliceListSaving.hasNext());
    }

    public SavingResponse processSave(SavingRequest savingRequest, int boardSavingId) throws ValidationException, NotFoundException {
        final var savingDto = savingMapper.savingRequestToSavingDto(savingRequest);

        final var resultOfValidation = this.savingValidationService.validate(savingDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var foundBoardSaving = boardSavingDao.fetchBoardSavingById(boardSavingId);
        final var newSavingToSave = attachInitRulesToNewSaving(savingDto, foundBoardSaving);
        final var response = savingDao.createSaving(newSavingToSave);

        return savingMapper.savingToSavingResponse(response);
    }

    public SavingResponse processUpdate(int savingId, SavingRequest savingRequest, int boardSavingId)
            throws NotFoundException, ValidationException {
        final var savingDtoToSave = savingMapper.savingRequestToSavingDto(savingRequest);
        final var resultOfValidation = savingValidationService.validate(savingDtoToSave);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var oldSaving = savingDao.fetchSavingById(savingId, boardSavingId);
        final var updatedSaving = savingMapper.updateSavingDtoToSaving(savingDtoToSave, oldSaving);
        final var response = savingDao.createSaving(updatedSaving);

        return savingMapper.savingToSavingResponse(response);
    }

    public void processDelete(int savingId) {
        transactionDao.removeAllTransactionsBySavingId(savingId);
        savingDao.deleteSaving(savingId);
    }

    public Saving updateSavingBalance(BigDecimal amountNewDeposit, int savingId, int boardSavingId) throws NotFoundException {
        final var savingForUpdateBalance = savingDao.fetchSavingById(savingId, boardSavingId);
        final var newBalance = savingForUpdateBalance.getSavingBalance().add(amountNewDeposit);
        savingForUpdateBalance.setSavingBalance(newBalance);

        this.updateSavingState(savingForUpdateBalance);
        return savingDao.createSaving(savingForUpdateBalance);
    }

    private void updateSavingState(Saving saving) {
        if (saving.getState() == SavingState.OVERDUE || saving.getTargetAmount() == null)
            return;

        if (saving.getSavingBalance().compareTo(saving.getTargetAmount()) >= 0) {
            saving.setState(SavingState.ACHIEVED);
        } else {
            saving.setState(SavingState.ACTIVE);
        }
    }

    public Saving attachInitRulesToNewSaving(SavingDto source, BoardSaving boardSaving) {
        final var rulesForSaving = new InitRulesSaving(SavingState.ACTIVE, BigDecimal.ZERO);

        ImageSaving newImage;
        if (source.image() == null)
            newImage = null;
        else
            newImage = new ImageSaving(source.image().imageType(), source.image().value());

        return new Saving(
                rulesForSaving, source.title(), source.currency(), source.description(), source.targetAmount(),
                source.deadline(), source.riskProfile(), newImage, boardSaving
        );
    }
}
