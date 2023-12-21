package co.grow.plan.academic.register.domain.academicplan.course.model;

import co.grow.plan.academic.register.shared.domain.exceptions.EmptyPropertyException;
import co.grow.plan.academic.register.shared.domain.interfaces.IEntity;

public record Course (int id, String name, long version)
    implements IEntity {
    @Override
    public void validate() throws EmptyPropertyException {
        if (this.name == null || this.name.isBlank()) {
            throw new EmptyPropertyException("name", "Course");
        }
    }
}