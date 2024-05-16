package co.grow.plan.academic.register.application.shared.helpers;

import co.grow.plan.academic.register.application.shared.exceptions.ApiBadInformationException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiError;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class EntitiesValidationsHelper {

    private static final Logger logger =
        Logger.getLogger(EntitiesValidationsHelper.class.getName());
    public static final String ID_ONE_NOT_NULL = "ID one cannot be null";
    public static final String ID_TWO_NOT_NULL = "ID two cannot be null";
    public static final String IDS_MUST_MATCH = "IDs must match";
    public static final String WRONG_ENTITY_VERSION = "Information version is different. ¡Try to refresh it!";

    public static void validateIdsMatchingOrException(Integer id1, Integer id2) {

        logger.log(Level.INFO, "Validating IDs");

        if (id1 == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    ID_ONE_NOT_NULL
                )
            );
        }

        if (id2 == null) {
            throw new ApiBadInformationException(
                new ApiError(
                    ID_TWO_NOT_NULL
                )
            );
        }

        if (!id1.equals(id2)) {
            throw new ApiBadInformationException(
                new ApiError(
                    IDS_MUST_MATCH
                )
            );
        }
    }

    public static void validateVersionsMatchOrException(long version1, long version2) {

        logger.log(Level.INFO, "Validating entity versions");

        if (version1 != version2) {
            throw new ApiConflictException(
                new ApiError(
                    String.format(
                        WRONG_ENTITY_VERSION)
                )
            );
        }
    }
}
