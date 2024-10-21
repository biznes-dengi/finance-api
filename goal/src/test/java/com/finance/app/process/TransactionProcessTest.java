package com.finance.app.process;

import com.finance.app.boundary.response.TransactionResponse;
import com.finance.app.dao.GoalDao;
import com.finance.app.dao.TransactionDao;
import com.finance.app.domain.Transaction;
import com.finance.app.domain.dto.TransactionDto;
import com.finance.app.exception.InternalError;
import com.finance.app.exception.NotFoundException;
import com.finance.app.exception.ParentException;
import com.finance.app.mapper.TransactionMapper;
import com.finance.app.generator.GeneratorDataTransaction;
import com.finance.app.mapper.TransactionMapperImpl;
import com.finance.app.mapper.context.GoalContext;
import com.finance.app.validation.ValidationResult;
import com.finance.app.generator.GeneratorDataGoal;
import com.finance.app.validation.service.TransactionValidationService;
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
    private GoalDao goalDaoMock;
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
        final int mockedGoalId = 1;
        final int mockerUserId = 1;
        final var goal = GeneratorDataTransaction.getTestData_testFindTransactions_01();

        // When
        when(goalDaoMock.fetchGoalById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(goal);
        when(transactionMapperMock.transactionToTransactionResponse(any())).thenAnswer((invocationOnMock) -> {
            final var responseTransaction = (Transaction) invocationOnMock.getArguments()[0];
            return new TransactionResponse(responseTransaction.getId(), responseTransaction.getType(),
                    responseTransaction.getDescription() , responseTransaction.getTransactionTimestamp(), null, null, responseTransaction.getAmount());
        });
        final var result = testObj.processGetById(expectedId, mockedGoalId, mockerUserId);

        // Then
        assertEquals(expectedId, result.id());
    }

    @Test
    @DisplayName("Check if it will throw NotFoundException, if it will search transaction by not exist id")
    void testFindTransactions_02() throws NotFoundException, InternalError {
        // Given
        final int expectedId = 11;
        final int mockedGoalId = 1;
        final int mockerUserId = 1;
        final var goal = GeneratorDataTransaction.getTestData_testFindTransactions_02();

        // When
        when(goalDaoMock.fetchGoalById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(goal);

        // Then
        assertThrows(NotFoundException.class,
                () -> this.testObj.processGetById(expectedId, mockedGoalId, mockerUserId));
    }

    @Test
    @DisplayName("Test processSave, create new transaction with all valid fields - Happy Path")
    void testProcessSave_01() throws ParentException {
        // Given
        final var payload = GeneratorDataTransaction.getTestData_testProcessSave_01_payload();
        final var linkedGoal = GeneratorDataGoal.getTestData_testProcessSave_01();
        final var goalId = 1;
        final var boardGoalId = 1;

        final var mapper = new TransactionMapperImpl();
        final var dto = mapper.transactionRequestToTransactionDto(payload);
        final var newTx = mapper.transactionDtoToTransaction(dto, new GoalContext(linkedGoal));
        final var expected = mapper.transactionToTransactionResponse(newTx);

        // When
        when(transactionMapperMock.transactionRequestToTransactionDto(payload))
                .thenReturn(dto);
        when(transactionMapperMock.transactionToTransactionResponse(any()))
                .thenReturn(expected);
        when(transactionMapperMock.transactionDtoToTransaction(any(), any()))
                .thenReturn(newTx);

        when(transactionValidationServiceMock.validate((TransactionDto) any())).thenReturn(ValidationResult.valid());
        when(proxyProcessMock.proxyToUpdateGoalBalance(any(), anyInt(), anyInt()))
                .thenReturn(linkedGoal);

        final var actual = testObj.processSave(payload, goalId, boardGoalId);

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
                .proxyToUpdateGoalBalance(dto.amount(), goalId, boardGoalId);
        verify(proxyProcessMock, times(1))
                .proxyToUpdateBoardBalance(boardGoalId, dto.amount());
        verify(transactionDaoNock, times(1))
                .createTransaction(any());
    }

    @Test
    @DisplayName("Test processSaveTransactionTransfer, create new transaction with type TRANSFER with all valid fields - Happy Path")
    void testProcessSaveTransactionTransfer_01() throws ParentException {
        // Given
        final var payload = GeneratorDataTransaction.getTestData_testProcessSaveTransactionTransfer_01();
        final var linkedGoal = GeneratorDataGoal.getTestData_testProcessSaveTransactionTransfer_01();

        final var boardGoalId = 1;

        final var mapper = new TransactionMapperImpl();
        final var dto = mapper.transactionTransferRequestToTransactionDto(payload);
        final var newTxFromGoal = mapper.transactionDtoToTransaction(dto, new GoalContext(linkedGoal.get(0)));
        final var newTxToGoal = mapper.transactionDtoToTransaction(dto, new GoalContext(linkedGoal.get(1)));
        final var expected = mapper.transactionToTransactionResponse(newTxFromGoal);

        // When
        when(transactionValidationServiceMock.validate((TransactionDto) any())).thenReturn(ValidationResult.valid());
        when(proxyProcessMock.proxyToUpdateGoalBalancesWhenDoTransferTransaction(anyInt(), anyInt(), anyInt(), any()))
                .thenReturn(linkedGoal);

        when(transactionMapperMock.transactionTransferRequestToTransactionDto(payload)).thenReturn(dto);
        when(transactionMapperMock.transactionDtoToTransaction(any(), any()))
                .thenReturn(newTxFromGoal)
                .thenReturn(newTxToGoal);
        when(transactionMapperMock.transactionToTransactionResponse(any())).thenReturn(expected);

        final var actual = testObj.processSaveTransactionTransfer(payload, boardGoalId);

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
                .proxyToUpdateGoalBalancesWhenDoTransferTransaction(
                        boardGoalId, dto.fromIdGoal(), dto.toIdGoal(), dto.amount()
                );
        verify(transactionDaoNock, times(2))
                .createTransaction(any());
    }
}
