package co.grow.plan.academic.register.shared.application.generics;

import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import co.grow.plan.academic.register.shared.domain.interfaces.IBasicEntity;

public abstract class BasicServiceForBasicEntity<
    E extends IBasicEntity,
    R extends IBasicRepositoryForBasicEntity<E>
    >
    extends BasicService<E, R>{
    public BasicServiceForBasicEntity(R repository) {
        super(repository);
    }

    protected void validateConstrains(Integer id, E entity) {

        var optionalCourse =
            this.getRepository().getByName(entity.name());

        if (id == null) { // It's creating
            if (optionalCourse.isPresent()) {
                throw new ApiConflictException(
                    new ApiError(
                        String.format("%s with same name already exists",
                            entity.getClass().getName())
                    )
                );
            }

        } else { // It's updating
            if (optionalCourse.isPresent() &&
                !id.equals(optionalCourse.get().id())) {
                throw new ApiConflictException(
                    new ApiError(
                        String.format("%s with same name already exists",
                            entity.getClass().getName())
                    )
                );
            }
        }
    }
}
