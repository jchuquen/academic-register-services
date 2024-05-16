package co.grow.plan.academic.register.application.shared.generics.services;

import co.grow.plan.academic.register.application.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.application.shared.exceptions.ApiError;
import co.grow.plan.academic.register.domain.shared.interfaces.BasicEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BasicServiceForBasicEntity<
    E extends BasicEntity,
    R extends BasicRepositoryForBasicEntity<E>
>
extends BasicServiceImpl<E, R> {

    private static final Logger logger =
        Logger.getLogger(BasicServiceForBasicEntity.class.getName());
    public static final String DUPLICATED_NAME = "%s with same name already exists";

    public BasicServiceForBasicEntity(R repository) {
        super(repository);
    }

    protected void validateConstrains(Integer id, E entity) {

        logger.log(Level.INFO, "Validating entity constrains using BasicServiceForBasicEntity");

        var optionalCourse =
            this.getRepository().getByName(entity.name());

        if (id == null) { // It's creating
            if (optionalCourse.isPresent()) {
                throw new ApiConflictException(
                    new ApiError(
                        String.format(DUPLICATED_NAME,
                            entity.getClass().getName())
                    )
                );
            }

        } else { // It's updating
            if (optionalCourse.isPresent() &&
                !id.equals(optionalCourse.get().id())) {
                throw new ApiConflictException(
                    new ApiError(
                        String.format(DUPLICATED_NAME,
                            entity.getClass().getName())
                    )
                );
            }
        }
    }
}
