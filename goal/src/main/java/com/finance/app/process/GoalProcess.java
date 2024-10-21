package com.finance.app.process;

import com.finance.app.boundary.request.GoalRequest;
import com.finance.app.boundary.response.GoalAllResponse;
import com.finance.app.boundary.response.GoalResponse;
import com.finance.app.dao.BoardGoalDao;
import com.finance.app.dao.GoalDao;
import com.finance.app.dao.TransactionDao;
import com.finance.app.domain.BoardGoal;
import com.finance.app.domain.ImageGoal;
import com.finance.app.domain.Goal;
import com.finance.app.domain.businessrules.InitRulesGoal;
import com.finance.app.domain.dto.GoalDto;
import com.finance.app.domain.enums.GoalState;
import com.finance.app.exception.ParentException;
import com.finance.app.exception.ValidationException;
import com.finance.app.mapper.GoalMapper;
import com.finance.app.validation.service.GoalValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalProcess {
    private final GoalDao goalDao;
    private final BoardGoalDao boardGoalDao;
    private final TransactionDao transactionDao;
    private final GoalValidationService goalValidationService;
    private final GoalMapper goalMapper;

    public GoalResponse processGetById(int goalId, int boardGoalId) throws ParentException {
        final var foundGoal = goalDao.fetchGoalById(goalId, boardGoalId);
        return goalMapper.goalToGoalResponse(foundGoal);
    }

    public GoalAllResponse processGetByState(GoalState state, int boardGoalId, int pageNumber) throws ParentException {
        final var foundSliceListGoal = goalDao.fetchGoalsByState(state, boardGoalId, pageNumber);
        final var mappedGoalViewResponse =
                goalMapper.goalListToGoalViewResponseList(foundSliceListGoal.getContent());
        return new GoalAllResponse(mappedGoalViewResponse, foundSliceListGoal.hasNext());
    }

    public GoalAllResponse processGetAll(int boardGoalId, int pageNumber) throws ParentException {
        final var foundSliceListGoal = goalDao.fetchAllGoals(boardGoalId, pageNumber);
        final var mappedGoalViewResponse =
                goalMapper.goalListToGoalViewResponseList(foundSliceListGoal.getContent());
        return new GoalAllResponse(mappedGoalViewResponse, foundSliceListGoal.hasNext());
    }

    public GoalResponse processSave(GoalRequest goalRequest, int boardGoalId) throws ParentException {
        final var goalDto = goalMapper.goalRequestToGoalDto(goalRequest);

        final var resultOfValidation = this.goalValidationService.validate(goalDto);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var foundBoardGoal = boardGoalDao.fetchBoardGoalById(boardGoalId);
        final var newGoalToSave = attachInitRulesToNewGoal(goalDto, foundBoardGoal);
        final var response = goalDao.createGoal(newGoalToSave);

        return goalMapper.goalToGoalResponse(response);
    }

    public GoalResponse processUpdate(int goalId, GoalRequest goalRequest, int boardGoalId)
            throws ParentException {
        final var goalDtoToSave = goalMapper.goalRequestToGoalDto(goalRequest);
        final var resultOfValidation = goalValidationService.validate(goalDtoToSave);
        if (resultOfValidation.notValid())
            throw new ValidationException(resultOfValidation.errorMsg());

        final var oldGoal = goalDao.fetchGoalById(goalId, boardGoalId);
        final var updatedGoal = goalMapper.updateGoalDtoToGoal(goalDtoToSave, oldGoal);
        final var response = goalDao.createGoal(updatedGoal);

        return goalMapper.goalToGoalResponse(response);
    }

    public void processDelete(int goalId) {
        transactionDao.removeAllTransactionsByGoalId(goalId);
        goalDao.deleteGoal(goalId);
    }

    public Goal updateGoalBalance(BigDecimal amountNewDeposit, int goalId, int boardGoalId)
            throws ParentException {
        final var goalForUpdateBalance = goalDao.fetchGoalById(goalId, boardGoalId);
        final var newBalance = goalForUpdateBalance.getGoalBalance().add(amountNewDeposit);
        goalForUpdateBalance.setGoalBalance(newBalance);

        this.updateGoalState(goalForUpdateBalance);
        return goalDao.createGoal(goalForUpdateBalance);
    }

    // TODO for transfer: we store two tx, first to "from" and second to "to"
    // TODO each tx will have different amount if each will have different currency
    public List<Goal> updateGoalBalancesWhenDoTransferTransaction(int boardGoalId, int fromGoalId, int toGoalId, BigDecimal amount)
            throws ParentException {
        final var responseFromGoal = updateGoalBalance(amount.multiply(BigDecimal.valueOf(-1)), fromGoalId, boardGoalId);
        final var responseToGoal = updateGoalBalance(amount, toGoalId, boardGoalId);
        return List.of(responseFromGoal, responseToGoal);
    }

    private void updateGoalState(Goal goal) {
        if (goal.getState() == GoalState.OVERDUE || goal.getTargetAmount() == null)
            return;

        if (goal.getGoalBalance().compareTo(goal.getTargetAmount()) >= 0) {
            goal.setState(GoalState.ACHIEVED);
        } else {
            goal.setState(GoalState.ACTIVE);
        }
    }

    public Goal attachInitRulesToNewGoal(GoalDto source, BoardGoal boardGoal) {
        final var rulesForGoal = new InitRulesGoal(GoalState.ACTIVE, BigDecimal.ZERO);

        ImageGoal newImage;
        if (source.image() == null)
            newImage = null;
        else
            newImage = new ImageGoal(source.image().imageType(), source.image().value());

        return new Goal(
                rulesForGoal, source.title(), source.currency(), source.targetAmount(),
                source.deadline(), source.riskProfile(), newImage, boardGoal
        );
    }
}
