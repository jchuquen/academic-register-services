package co.grow.plan.academic.register.domain.academicplan.period.model;

import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PeriodTest {

    @Test
    public void shouldThrowExceptionWhenNameIsNull () {
        Period period = new Period(1, null, null, 0L);

        assertThrows(
            EmptyPropertyException.class,
            period::validate
        );
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        Period period = new Period(1, "   ", null, 0L);

        assertThrows(
            EmptyPropertyException.class,
            period::validate
        );
    }

    @Test
    public void shouldThrowExceptionWhenActiveIsNull () {
        Period period = new Period(1, "2023/I", null, 0L);

        assertThrows(
            EmptyPropertyException.class,
            period::validate
        );
    }

    @Test
    public void shouldNotThrowExceptionWhenNameAndActiveNotNullAndNotEmpty() {
        Period period =
            new Period(1, "2023/I", true,  0L);

        assertDoesNotThrow(
            period::validate
        );

        period =
            new Period(1, "2023/I", false,  0L);

        assertDoesNotThrow(
            period::validate
        );
    }
}
