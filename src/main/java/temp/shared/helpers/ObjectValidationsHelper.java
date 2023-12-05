package temp.shared.helpers;

import co.grow.plan.academic.register.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;

public class ObjectValidationsHelper {
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
