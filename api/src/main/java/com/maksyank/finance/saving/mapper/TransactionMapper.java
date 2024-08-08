package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.dto.TransactionDto;
import com.maksyank.finance.saving.dto.TransactionUpdateDto;
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

    TransactionDto transactionRequestToTransactionDto(TransactionRequest source);

    TransactionUpdateDto transactionUpdateRequestToTransactionUpdateDto(TransactionUpdateRequest source);
}
