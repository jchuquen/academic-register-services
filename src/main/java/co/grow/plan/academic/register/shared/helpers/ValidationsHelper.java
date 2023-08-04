package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;

public class ValidationsHelper {
    public static void validateIdsMatchingOrException(Integer id1, Integer id2) {
        if (id1 == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    "Parameter ID cannot be null"
                )
            );
        }

        if (id2 == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    "Object ID cannot be null"
                )
            );
        }

        if (!id1.equals(id2)) {
            throw new ApiBadInformationException(
                new ApiError(
                    "Parameter ID and object ID must match"
                )
            );
        }
    }

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
