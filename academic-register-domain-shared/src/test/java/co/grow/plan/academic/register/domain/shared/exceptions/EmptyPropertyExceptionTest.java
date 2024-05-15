package co.grow.plan.academic.register.domain.shared.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmptyPropertyExceptionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void throwExceptionWithProperMessage() {
        String property = "Title";
        String entityName = "Book";
        String expectedMessage =
        String.format(EmptyPropertyException.MESSAGE_FORMAT, property, entityName);

        EmptyPropertyException exception =
            new EmptyPropertyException(property, entityName);

        assertEquals(expectedMessage, exception.getMessage());
    }
}