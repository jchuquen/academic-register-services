package co.grow.plan.academic.register.application.shared.tests.helpers;

import co.grow.plan.academic.register.application.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiError;
import co.grow.plan.academic.register.application.shared.helpers.ObjectValidationsHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
