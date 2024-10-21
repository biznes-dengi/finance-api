package com.finance.app.mapper;

import com.finance.app.boundary.request.TransactionRequest;
import com.finance.app.boundary.request.TransactionTransferRequest;
import com.finance.app.boundary.request.TransactionUpdateRequest;
import com.finance.app.boundary.response.TransactionResponse;
import com.finance.app.boundary.response.TransactionViewResponse;
import com.finance.app.domain.Transaction;
import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.domain.dto.TransactionUpdateDto;
import com.finance.app.mapper.context.GoalContext;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface TransactionMapper {
    TransactionResponse transactionToTransactionResponse(Transaction source);

    TransactionViewResponse transactionToTransactionViewResponse(Transaction source);

    List<TransactionViewResponse> transactionListToTransactionViewResponseList(List<Transaction> source);

    @Mapping(target = "fromIdGoal", ignore = true)
    @Mapping(target = "toIdGoal", ignore = true)
    @Mapping(source = "date", target = "transactionTimestamp")
    TransactionDto transactionRequestToTransactionDto(TransactionRequest source);

    @Mapping(target = "type", constant = "TRANSFER")
    // TODO here mocked. when will logic of calculate of currency, fix
    @Mapping(source = "fromGoalAmount", target = "amount")
    @Mapping(source = "date", target = "transactionTimestamp")
    TransactionDto transactionTransferRequestToTransactionDto(TransactionTransferRequest source);

    TransactionUpdateDto transactionUpdateRequestToTransactionUpdateDto(TransactionUpdateRequest source);

    @Mapping(target = "goal", expression = "java(linkedGoal.goal)")
    @Mapping(target = "id", ignore = true)
    Transaction transactionDtoToTransaction(TransactionDto source, @Context GoalContext linkedGoal);
}
