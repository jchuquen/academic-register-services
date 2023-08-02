package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationsHelperTest {

    @Test
    @DisplayName(
        "ValidateIdsMatchingOrExceptionNoException - Should throw ApiBadInformationException " +
        "when some parameter is null")
    public void testValidateIdsMatchingOrExceptionThrowsWhenNullParameters() {
        assertThrows(
            ApiBadInformationException.class,
            () -> ValidationsHelper.validateIdsMatchingOrException(null, null)
        );

        assertThrows(
            ApiBadInformationException.class,
            () -> ValidationsHelper.validateIdsMatchingOrException(null, 99999)
        );

        assertThrows(
            ApiBadInformationException.class,
            () -> ValidationsHelper.validateIdsMatchingOrException(99999, null)
        );
    }
    @Test
    @DisplayName(
        "ValidateIdsMatchingOrExceptionNoException - Should validate Ids " +
        "and no throw exception")
    public void testValidateIdsMatchingOrExceptionNoException() {
        assertDoesNotThrow(() ->
            ValidationsHelper.validateIdsMatchingOrException(0, 0)
        );

        assertDoesNotThrow(() ->
            ValidationsHelper.validateIdsMatchingOrException(9999, 9999)
        );
    }

    @Test
    @DisplayName(
        "ValidateIdsMatchingOrExceptionNoException - Should indicate parameter 1 " +
        "cannot be null")
    public void testValidateIdsMatchingOrExceptionMessageParam1And2Null() {
        try {
            ValidationsHelper.validateIdsMatchingOrException(null, null);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("Parameter ID cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }

    @Test
    @DisplayName(
        "ValidateIdsMatchingOrExceptionNoException - Should indicate parameter 1 " +
            "cannot be null")
    public void testValidateIdsMatchingOrExceptionMessageParam1Null() {
        try {
            ValidationsHelper.validateIdsMatchingOrException(null, 9999);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("Parameter ID cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }

    @Test
    @DisplayName(
        "ValidateIdsMatchingOrExceptionNoException - Should indicate parameter 2 " +
            "cannot be null")
    public void testValidateIdsMatchingOrExceptionMessageParam2Null() {
        try {
            ValidationsHelper.validateIdsMatchingOrException(99999, null);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("Object ID cannot be null",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }

    @Test
    @DisplayName(
        "ValidateIdsMatchingOrExceptionNoException - Should indicate parameter 1 and 2 " +
            "must match")
    public void testValidateIdsMatchingOrExceptionMessageParamsMustMatch() {
        try {
            ValidationsHelper.validateIdsMatchingOrException(99999, 11111);
            throw new RuntimeException("It must not call this");
        } catch (Exception ex) {
            assertSame(ex.getClass(), ApiBadInformationException.class);

            ApiBadInformationException apiException
                = (ApiBadInformationException) ex;

            ApiError apiError = apiException.getApiError();
            assertNotNull(apiError);

            assertEquals("Parameter ID and object ID must match",
                apiError.getMainMessage());

            assertNull(apiError.getDetailedErrors());
        }
    }
}
