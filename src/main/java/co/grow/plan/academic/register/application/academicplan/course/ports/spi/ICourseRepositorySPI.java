package co.grow.plan.academic.register.application.academicplan.course.ports.spi;

import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.generics.IBasicRepositoryForBasicEntity;

public interface ICourseRepositorySPI
    extends IBasicRepositoryForBasicEntity<Course> {
}
