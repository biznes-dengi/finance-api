package com.maksyank.finance.saving.mapper;

import com.maksyank.finance.saving.boundary.request.TransactionRequest;
import com.maksyank.finance.saving.boundary.request.TransactionUpdateRequest;
import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.boundary.response.TransactionViewResponse;
import com.maksyank.finance.saving.domain.Saving;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.dto.TransactionDto;
import com.maksyank.finance.saving.dto.TransactionUpdateDto;

import java.util.Collection;
import java.util.List;

public class TransactionMapper {

    public static TransactionResponse entityToResponse(Transaction source) {
        return new TransactionResponse(source.getId(), source.getType(), source.getDescription(), source.getDeal_date(), source.getAmount());
    }

    public static TransactionViewResponse entityToViewResponse(Transaction source) {
        return new TransactionViewResponse(source.getId(), source.getType(), source.getDeal_date(), source.getAmount());
    }

    public static List<TransactionViewResponse> entityToViewResponse(Collection<Transaction> source) {
        return source.stream().map(TransactionMapper::entityToViewResponse).toList();
    }

    public static Transaction mapToNewEntity(TransactionDto source, Saving saving) {
        return new Transaction(source.type(), source.description(), source.fundingDate(), source.amount(), saving);
    }

    public static TransactionDto mapToDto(TransactionRequest source) {
        return new TransactionDto(source.type(), source.description(), source.fundingDate(), source.amount());
    }

    public static TransactionUpdateDto mapToUpdateDto(TransactionUpdateRequest source) {
        return new TransactionUpdateDto(source.description());
    }
}
