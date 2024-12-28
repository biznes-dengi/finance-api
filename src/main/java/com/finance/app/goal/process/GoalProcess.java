package com.finance.app.goal.process;

import com.finance.app.goal.boundary.request.GoalRequest;
import com.finance.app.goal.boundary.response.GoalAllResponse;
import com.finance.app.goal.boundary.response.GoalResponse;
import com.finance.app.goal.dao.BoardGoalDao;
import com.finance.app.goal.dao.GoalDao;
import com.finance.app.goal.dao.TransactionDao;
import com.finance.app.goal.domain.BoardGoal;
import com.finance.app.goal.domain.ImageGoal;
import com.finance.app.goal.domain.Goal;
import com.finance.app.goal.domain.businessrules.InitRulesGoal;
import com.finance.app.goal.domain.dto.GoalDto;
import com.finance.app.goal.domain.dto.TransactionTransferDto;
import com.finance.app.goal.domain.enums.GoalState;
import com.finance.app.goal.domain.enums.TransactionType;
import com.finance.app.exception.ParentException;
import com.finance.app.exception.ValidationException;
import com.finance.app.goal.mapper.GoalMapper;
import com.finance.app.goal.validation.service.GoalValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ProxyGoalAndBoard proxyToBoard;

    public GoalResponse processGetById(int goalId, int boardGoalId) throws ParentException {
        final var foundGoal = goalDao.fetchGoalById(goalId, boardGoalId);
        return goalMapper.goalToGoalResponse(foundGoal);
    }

    public GoalAllResponse processGetByState(GoalState state, int boardGoalId, int pageNumber, Integer pageSize) throws ParentException {
        final var foundSliceListGoal = goalDao.fetchGoalsByState(state, boardGoalId, pageNumber, pageSize);
        final var mappedGoalViewResponse =
                goalMapper.goalListToGoalViewResponseList(foundSliceListGoal.getContent());
        return new GoalAllResponse(mappedGoalViewResponse, foundSliceListGoal.hasNext());
    }

    public GoalAllResponse processGetAll(int boardGoalId, int pageNumber, Integer pageSize) throws ParentException {
        final var foundSliceListGoal = goalDao.fetchAllGoals(boardGoalId, pageNumber, pageSize);
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

    @Transactional(
            rollbackFor = {Exception.class, Error.class, RuntimeException.class}
    )
    public void processDelete(int goalId, int boardGoalId) throws ParentException {
        final var goalToRemove = goalDao.fetchGoalById(goalId, boardGoalId);
        proxyToBoard.proxyToUpdateBoardBalance(TransactionType.WITHDRAW, goalToRemove.getGoalBalance(), boardGoalId);

        transactionDao.removeAllTransactionsByGoalId(goalId);
        goalDao.deleteGoal(goalId);
    }

    public Goal updateGoalBalance(TransactionType type, BigDecimal amount, int goalId, int boardGoalId) throws ParentException {
        if (type == TransactionType.DEPOSIT)
            return calculateNewGoalBalance(amount, goalId, boardGoalId);
        else
            return calculateNewGoalBalance(amount.multiply(BigDecimal.valueOf(-1)), goalId, boardGoalId);
    }

    public List<Goal> updateGoalBalancesByTransferTransaction(TransactionTransferDto transferDto, int boardGoalId)
            throws ParentException {
        final var responseFromGoal = updateGoalBalance(TransactionType.WITHDRAW, transferDto.getFromGoalAmount(), transferDto.getFromIdGoal(), boardGoalId);
        final var responseToGoal = updateGoalBalance(TransactionType.DEPOSIT, transferDto.getToGoalAmount(), transferDto.getToIdGoal(), boardGoalId);
        return List.of(responseFromGoal, responseToGoal);
    }

    private Goal calculateNewGoalBalance(BigDecimal value, int goalId, int boardGoalId) throws ParentException {
        final var goal = goalDao.fetchGoalById(goalId, boardGoalId);
        final var newBalance = goal.getGoalBalance().add(value);
        goal.setGoalBalance(newBalance);
        this.updateGoalState(goal);
        return goalDao.createGoal(goal);
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
