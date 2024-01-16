package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public final class ObjectValidationsHelperTest {

    @Test
    public void shouldValidateNullObjects() {
        try{
            ObjectValidationsHelper.validateNotNull(null, "Test");
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("Parameter Test cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }

    }

    @Test
    public void shouldNotGenerateExceptionWithNotNullObject() {
        assertDoesNotThrow( () ->
            ObjectValidationsHelper.validateNotNull(new Object(), "Test")
        );
    }
}
