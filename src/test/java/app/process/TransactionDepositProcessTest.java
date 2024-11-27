package app.process;

import com.finance.app.boundary.request.DepositAmountRequest;
import com.finance.app.dao.GoalDao;
import com.finance.app.exception.NotFoundException;
import com.finance.app.exception.ParentException;
import app.generator.GeneratorDataTransaction;
import com.finance.app.validation.ValidationResult;
import com.finance.app.validation.service.TransactionDepositValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDepositProcessTest {
    @Mock
    private GoalDao goalDao;
    @Mock
    private TransactionDepositValidationService validator;
    @InjectMocks
    private TransactionDepositProcess transactionDepositProcess;

    @Test
    @DisplayName("Check if fund deposits will be summed correctly")
    void testProcessGetDepositAmountByMonth_01() throws ParentException {
        // Given
        final int year = 2023;
        final int month = 3;
        final var goal = GeneratorDataTransaction.getTestData_testProcessGetDepositAmountByMonth_01();

        // When
        when(goalDao.fetchGoalById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(goal);
        when(validator.validate(any())).thenReturn(ValidationResult.valid());
        final var result = transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(1, year, month, 1));

        // Then
        assertEquals(BigDecimal.valueOf(3447.22), result);
    }

    @Test
    @DisplayName("Check if it will throw NotFoundException, if the month do not have any deposits")
    void testProcessGetDepositAmountByMonth_02() throws ParentException {
        // Given
        final int year = 2023;
        final int month = 8;
        final var goal = GeneratorDataTransaction.getTestData_testProcessGetDepositAmountByMonth_02();

        // When
        when(goalDao.fetchGoalById(Mockito.anyInt(), Mockito.anyInt())).thenReturn(goal);
        when(validator.validate(any())).thenReturn(ValidationResult.valid());

        // Then
        assertThrows(NotFoundException.class,
                () -> transactionDepositProcess.processGetFundAmountByMonth(new DepositAmountRequest(1, year, month, 1)));
    }
}
