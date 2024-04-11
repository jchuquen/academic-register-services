package co.grow.plan.academic.register.application.shared.helpers;

import co.grow.plan.academic.register.application.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiError;

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
