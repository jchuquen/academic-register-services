package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;

public class ValidationsHelper {
    public static void validateIdsMatchingOrException(Integer id1, Integer id2) {
        if (!id1.equals(id2)) {
            throw new ApiBadInformationException(
                new ApiError(
                    "Parameter ID and object ID must match"
                )
            );
        }
    }
}
