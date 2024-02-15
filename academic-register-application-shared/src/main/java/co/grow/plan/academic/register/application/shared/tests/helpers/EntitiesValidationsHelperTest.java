package co.grow.plan.academic.register.application.shared.tests.helpers;

import co.grow.plan.academic.register.application.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiError;
import co.grow.plan.academic.register.application.shared.helpers.EntitiesValidationsHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class EntitiesValidationsHelperTest {

    @Test
    public void shouldThrowExceptionWhenSomeParameterIsNull() {
        assertThrows(
            ApiBadInformationException.class,
            () -> EntitiesValidationsHelper.validateIdsMatchingOrException(null, null)
        );

        assertThrows(
            ApiBadInformationException.class,
            () -> EntitiesValidationsHelper.validateIdsMatchingOrException(null, 99999)
        );

        assertThrows(
            ApiBadInformationException.class,
            () -> EntitiesValidationsHelper.validateIdsMatchingOrException(99999, null)
        );
    }
    @Test
    public void shouldValidateIdsAndNoThrowException() {
        assertDoesNotThrow(() ->
            EntitiesValidationsHelper.validateIdsMatchingOrException(0, 0)
        );

        assertDoesNotThrow(() ->
            EntitiesValidationsHelper.validateIdsMatchingOrException(9999, 9999)
        );
    }

    @Test
    public void shouldIndicateParameter1CannotBeNull() {
        try {
            EntitiesValidationsHelper.validateIdsMatchingOrException(null, null);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("ID one cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }

        try {
            EntitiesValidationsHelper.validateIdsMatchingOrException(null, 9999);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("ID one cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }

    @Test
    public void shouldIndicateParameter2CannotBeNull() {
        try {
            EntitiesValidationsHelper.validateIdsMatchingOrException(99999, null);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("ID two cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }

    @Test
    public void shouldIndicateParameter1And2MustMatch() {
        try {
            EntitiesValidationsHelper.validateIdsMatchingOrException(99999, 11111);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("IDs must match",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }
}
