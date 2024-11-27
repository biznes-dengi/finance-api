package com.finance.app.goal.mapper;

import com.finance.app.goal.boundary.request.TransactionRequest;
import com.finance.app.goal.boundary.request.TransactionTransferRequest;
import com.finance.app.goal.boundary.request.TransactionUpdateRequest;
import com.finance.app.goal.boundary.response.transaction.TransactionNormalResponse;
import com.finance.app.goal.boundary.response.transaction.TransactionTransferResponse;
import com.finance.app.goal.domain.Transaction;
import com.finance.app.goal.domain.dto.TransactionDto;
import com.finance.app.goal.domain.dto.TransactionTransferDto;
import com.finance.app.goal.domain.dto.TransactionUpdateDto;
import com.finance.app.goal.mapper.context.GoalContext;
import com.finance.app.goal.mapper.context.GoalsNameTransferContext;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface TransactionMapper {
    TransactionNormalResponse transactionToTransactionResponse(Transaction source);

    @Mapping(source = "date", target = "transactionTimestamp")
    TransactionDto transactionRequestToTransactionDto(TransactionRequest source);

    TransactionTransferDto transactionTransferRequestToTransactionTransferDto(TransactionTransferRequest source);

    TransactionUpdateDto transactionUpdateRequestToTransactionUpdateDto(TransactionUpdateRequest source);

    @Mapping(target = "goal", expression = "java(linkedGoal.goal)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fromIdGoal", ignore = true)
    @Mapping(target = "fromGoalAmount", ignore = true)
    @Mapping(target = "toIdGoal", ignore = true)
    @Mapping(target = "toGoalAmount", ignore = true)
    Transaction transactionDtoToTransaction(TransactionDto source, @Context GoalContext linkedGoal);

    @Mapping(target = "goal", expression = "java(linkedGoal.goal)")
    @Mapping(target = "type", constant = "TRANSFER")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", ignore = true)
    Transaction transactionTransferDtoToTransaction(TransactionTransferDto source, @Context GoalContext linkedGoal);

    @Mapping(target = "fromGoalName", expression = "java(linkedNamesOfGoals.fromGoalName)")
    @Mapping(target = "toGoalName", expression = "java(linkedNamesOfGoals.toGoalName)")
    TransactionTransferResponse transactionToTransactionTransferResponse(Transaction source, @Context GoalsNameTransferContext linkedNamesOfGoals);
}
