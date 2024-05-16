package co.grow.plan.academic.register.application.shared.helpers;

import co.grow.plan.academic.register.application.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiError;
import co.grow.plan.academic.register.application.shared.generics.services.BasicServiceImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ObjectValidationsHelper {

    private static final Logger logger = Logger.getLogger(BasicServiceImpl.class.getName());
    public static final String PARAMETER_CANNOT_BE_NULL = "Parameter %s cannot be null";

    public static void validateNotNull(Object object, String name) {
        logger.log(Level.INFO, "Validating object not null");
        if (object == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    String.format(PARAMETER_CANNOT_BE_NULL, name)
                )
            );
        }
    }
}
