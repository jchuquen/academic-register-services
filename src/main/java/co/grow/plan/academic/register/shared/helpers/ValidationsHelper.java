package co.grow.plan.academic.register.shared.helpers;

import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;

public class ValidationsHelper {
    public static void validateIdsMatchingOrException(Integer id1, Integer id2) {
        if (id1 == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    "ID one cannot be null"
                )
            );
        }

        if (id2 == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    "ID two cannot be null"
                )
            );
        }

        if (!id1.equals(id2)) {
            throw new ApiBadInformationException(
                new ApiError(
                    "IDs must match"
                )
            );
        }
    }

    public static void validateVersionsMatchOrException(long version1, long version2) {

        if (version1 != version2) {
            throw new ApiConflictException(
                new ApiError(
                    String.format(
                        "Information version is different. ¡Try to refresh it!")
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
