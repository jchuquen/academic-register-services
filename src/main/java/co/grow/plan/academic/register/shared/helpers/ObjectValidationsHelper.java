package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.application.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiError;

public final class ObjectValidationsHelper {
    public static void validateNotNull(Object object, String name) {
        if (object == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    String.format("Parameter %s cannot be null", name)
                )
            );
        }
    }
}
