package com.maksyank.finance.saving.process;

import com.maksyank.finance.saving.boundary.response.TransactionResponse;
import com.maksyank.finance.saving.dao.SavingDao;
import com.maksyank.finance.saving.dao.TransactionDao;
import com.maksyank.finance.saving.domain.Transaction;
import com.maksyank.finance.saving.domain.dto.TransactionDto;
import com.maksyank.finance.saving.exception.InternalError;
import com.maksyank.finance.saving.exception.NotFoundException;
import com.maksyank.finance.saving.exception.ParentException;
import com.maksyank.finance.saving.mapper.TransactionMapper;
import com.maksyank.finance.saving.mapper.TransactionMapperImpl;
import com.maksyank.finance.saving.mapper.context.SavingContext;
import com.maksyank.finance.saving.validation.ValidationResult;
import com.maksyank.finance.utility.generator.GeneratorDataSaving;
import com.maksyank.finance.utility.generator.GeneratorDataTransaction;
import com.maksyank.finance.saving.validation.service.TransactionValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionProcessTest {
    @Mock
    private SavingDao savingDaoMock;
    @InjectMocks
    private TransactionProcess testObj;
    @Mock
    private TransactionValidationService transactionValidationServiceMock;
    @Mock
    private ProxyProcess proxyProcessMock;
    @Mock
    private TransactionDao transactionDaoNock;
    @Mock
    private TransactionMapper transactionMapperMock;

    @Test
    @DisplayName("Check if it will be found transaction by id")
    void testFindTransactions_01() throws NotFoundException, InternalError {
        // Given
        final int expectedId = 102;
        final int mockedSavingId = 1;
        final int mockerUserId = 1;
        final var saving = GeneratorDataTransaction.getTestData_testFindTransactions_01();

        // When
        when(savingDaoMock.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);
        when(transactionMapperMock.transactionToTransactionResponse(any())).thenAnswer((invocationOnMock) -> {
            final var responseTransaction = (Transaction) invocationOnMock.getArguments()[0];
            return new TransactionResponse(responseTransaction.getId(), responseTransaction.getType(),
                    responseTransaction.getDescription() , responseTransaction.getTransactionTimestamp(), null, null, responseTransaction.getAmount());
        });
        final var result = testObj.processGetById(expectedId, mockedSavingId, mockerUserId);

        // Then
        assertEquals(expectedId, result.id());
    }

    @Test
    @DisplayName("Check if it will throw NotFoundException, if it will search transaction by not exist id")
    void testFindTransactions_02() throws NotFoundException, InternalError {
        // Given
        final int expectedId = 11;
        final int mockedSavingId = 1;
        final int mockerUserId = 1;
        final var saving = GeneratorDataTransaction.getTestData_testFindTransactions_02();

        // When
        when(savingDaoMock.fetchSavingById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(saving);

        // Then
        assertThrows(NotFoundException.class,
                () -> this.testObj.processGetById(expectedId, mockedSavingId, mockerUserId));
    }

    @Test
    @DisplayName("Test processSave, create new transaction with all valid fields - Happy Path")
    void testProcessSave_01() throws ParentException {
        // Given
        final var payload = GeneratorDataTransaction.getTestData_testProcessSave_01_payload();
        final var linkedSaving = GeneratorDataSaving.getTestData_testProcessSave_01();
        final var savingId = 1;
        final var boardSavingId = 1;

        final var mapper = new TransactionMapperImpl();
        final var dto = mapper.transactionRequestToTransactionDto(payload);
        final var newTx = mapper.transactionDtoToTransaction(dto, new SavingContext(linkedSaving));
        final var expected = mapper.transactionToTransactionResponse(newTx);

        // When
        when(transactionMapperMock.transactionRequestToTransactionDto(payload))
                .thenReturn(dto);
        when(transactionMapperMock.transactionToTransactionResponse(any()))
                .thenReturn(expected);
        when(transactionMapperMock.transactionDtoToTransaction(any(), any()))
                .thenReturn(newTx);

        when(transactionValidationServiceMock.validate((TransactionDto) any())).thenReturn(ValidationResult.valid());
        when(proxyProcessMock.proxyToUpdateSavingBalance(any(), anyInt(), anyInt()))
                .thenReturn(linkedSaving);

        final var actual = testObj.processSave(payload, savingId, boardSavingId);

        // Then
        assertEquals(expected.type(), actual.type());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.transactionTimestamp(), actual.transactionTimestamp());
        assertNull(actual.fromIdGoal());
        assertNull(actual.toIdGoal());
        assertEquals(expected.amount(), actual.amount());

        verify(transactionMapperMock, times(1))
                .transactionToTransactionResponse(any());
        verify(transactionMapperMock, times(1))
                .transactionRequestToTransactionDto(payload);
        verify(transactionMapperMock, times(1))
                .transactionDtoToTransaction(any(), any());

        verify(proxyProcessMock, times(1))
                .proxyToUpdateSavingBalance(dto.amount(), savingId, boardSavingId);
        verify(proxyProcessMock, times(1))
                .proxyToUpdateBoardBalance(boardSavingId, dto.amount());
        verify(transactionDaoNock, times(1))
                .createTransaction(any());
    }

    @Test
    @DisplayName("Test processSaveTransactionTransfer, create new transaction with type TRANSFER with all valid fields - Happy Path")
    void testProcessSaveTransactionTransfer_01() throws ParentException {
        // Given
        final var payload = GeneratorDataTransaction.getTestData_testProcessSaveTransactionTransfer_01();
        final var linkedSaving = GeneratorDataSaving.getTestData_testProcessSaveTransactionTransfer_01();

        final var boardSavingId = 1;

        final var mapper = new TransactionMapperImpl();
        final var dto = mapper.transactionTransferRequestToTransactionDto(payload);
        final var newTxFromSaving = mapper.transactionDtoToTransaction(dto, new SavingContext(linkedSaving.get(0)));
        final var newTxToSaving = mapper.transactionDtoToTransaction(dto, new SavingContext(linkedSaving.get(1)));
        final var expected = mapper.transactionToTransactionResponse(newTxFromSaving);

        // When
        when(transactionValidationServiceMock.validate((TransactionDto) any())).thenReturn(ValidationResult.valid());
        when(proxyProcessMock.proxyToUpdateSavingBalancesWhenDoTransferTransaction(anyInt(), anyInt(), anyInt(), any()))
                .thenReturn(linkedSaving);

        when(transactionMapperMock.transactionTransferRequestToTransactionDto(payload)).thenReturn(dto);
        when(transactionMapperMock.transactionDtoToTransaction(any(), any()))
                .thenReturn(newTxFromSaving)
                .thenReturn(newTxToSaving);
        when(transactionMapperMock.transactionToTransactionResponse(any())).thenReturn(expected);

        final var actual = testObj.processSaveTransactionTransfer(payload, boardSavingId);

        // Then
        assertEquals(expected.type(), actual.type());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.transactionTimestamp(), actual.transactionTimestamp());
        assertEquals(expected.fromIdGoal(), actual.fromIdGoal());
        assertEquals(expected.toIdGoal(), actual.toIdGoal());
        assertEquals(expected.amount(), actual.amount());

        verify(transactionMapperMock, times(1))
                .transactionTransferRequestToTransactionDto(payload);
        verify(transactionMapperMock, times(1))
                .transactionToTransactionResponse(any());
        verify(transactionMapperMock, times(2))
                .transactionDtoToTransaction(any(), any());

        verify(proxyProcessMock, times(1))
                .proxyToUpdateSavingBalancesWhenDoTransferTransaction(
                        boardSavingId, dto.fromIdGoal(), dto.toIdGoal(), dto.amount()
                );
        verify(transactionDaoNock, times(2))
                .createTransaction(any());
    }
}
