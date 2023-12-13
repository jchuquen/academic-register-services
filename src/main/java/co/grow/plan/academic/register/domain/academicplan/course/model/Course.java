package co.grow.plan.academic.register.domain.academicplan.course.model;

import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import co.grow.plan.academic.register.shared.domain.exceptions.ApiMissingInformationException;
import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;

public record Course (int id, String name, long version)
    implements IEntity {
    @Override
    public void validate() {
        if (this.name == null || this.name.isBlank()) {
            throw new ApiMissingInformationException(
                new ApiError(
                    "Field 'name' is required in 'Course' entity"
                )
            );
        }
    }
}
