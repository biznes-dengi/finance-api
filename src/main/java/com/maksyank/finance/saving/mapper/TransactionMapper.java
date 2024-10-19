package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionTransferRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.domain.dto.TransactionUpdateDto;
import com.maksyank.finance.saving.mapper.context.SavingContext;
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

    @Mapping(target = "saving", expression = "java(linkedSaving.saving)")
    @Mapping(target = "id", ignore = true)
    Transaction transactionDtoToTransaction(TransactionDto source, @Context SavingContext linkedSaving);
}
