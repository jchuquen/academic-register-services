package co.grow.plan.academic.register.domain.admissions.identificationtype.model;

import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdentificationTypeTest {

    @Test
    public void shouldThrowExceptionWhenNameIsNull () {
        IdentificationType identificationType =
            new IdentificationType(1, null, 0L);

        assertThrows(
            EmptyPropertyException.class,
            identificationType::validate
        );
    }

    @Test
    public void shouldThrowExceptionWhenNameIsEmpty() {
        IdentificationType identificationType =
            new IdentificationType(1, "   ", 0L);

        assertThrows(
            EmptyPropertyException.class,
            identificationType::validate
        );
    }

    @Test
    public void shouldNotThrowExceptionWhenNameNotNullAndNotEmpty() {
        IdentificationType identificationType =
            new IdentificationType(1, "Software Development", 0L);

        assertDoesNotThrow(
            identificationType::validate
        );
    }
}
