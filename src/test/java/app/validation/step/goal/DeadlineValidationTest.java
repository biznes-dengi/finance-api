package app.validation.step.goal;

import com.finance.app.domain.dto.GoalDto;
import com.finance.app.validation.step.ValidationStep;
import app.generator.GeneratorDataGoal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineValidationTest {

    @Test
    @DisplayName(value = "Test Deadline step, check if deadline has all valid data")
    public void testDeadlineValidationStep_AllValidData() {
        // Given
        ValidationStep<GoalDto> stepDeadline = new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount()
                .linkWith(new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate());
        final var goalToValid = GeneratorDataGoal.getTestData_testDeadlineValidationStep_AllValidData();

        // When
        final var response = stepDeadline.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Deadline step, check if targetAmount is null & deadline exists")
    public void testDeadlineValidationStep_01() {
        // Given
        ValidationStep<GoalDto> stepDeadline = new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount();
        final var goalToValid = GeneratorDataGoal.getTestData_testDeadlineValidationStep_01();

        // When
        final var response = stepDeadline.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'deadline' field can not exist without field 'targetAmount'", response.errorMsg());
    }

    @Test
    @DisplayName(value = "Test Deadline step, check if deadline is null")
    public void testDeadlineValidationStep_02() {
        // Given
        ValidationStep<GoalDto> stepDeadline = new DeadlineValidation.StepValidIfItNotExistsWithoutTargetAmount()
                .linkWith(new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate());
        final var goalToValid = GeneratorDataGoal.getTestData_testDeadlineValidationStep_02();

        // When
        final var response = stepDeadline.validate(goalToValid);

        // Then
        assertTrue(response.isValid());
    }

    @Test
    @DisplayName(value = "Test Deadline step, check if deadline is less than current day")
    public void testDeadlineValidationStep_03() {
        // Given
        ValidationStep<GoalDto> stepDeadline = new DeadlineValidation.StepValidIfDeadlineGreaterThenCurrentDate();
        final var goalToValid = GeneratorDataGoal.getTestData_testDeadlineValidationStep_03();

        // When
        final var response = stepDeadline.validate(goalToValid);

        // Then
        assertFalse(response.isValid());
        assertEquals("The 'deadline' field must contain at least the next day.", response.errorMsg());
    }
}
