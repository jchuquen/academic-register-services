package co.grow.plan.academic.register.domain.academicplan.subject.model;

import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubjectTest {

    @Test
    public void shouldThrowExceptionWhenNameIsNull () {
        Subject subject = new Subject(1, null, 0L);

        assertThrows(
            EmptyPropertyException.class,
            subject::validate
        );
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        Subject subject = new Subject(1, "   ", 0L);

        assertThrows(
            EmptyPropertyException.class,
            subject::validate
        );
    }

    @Test
    public void shouldNotThrowExceptionWhenNameNotNullAndNotEmpty() {
        Subject subject =
            new Subject(1, "Software Development", 0L);

        assertDoesNotThrow(
            subject::validate
        );
    }
}
