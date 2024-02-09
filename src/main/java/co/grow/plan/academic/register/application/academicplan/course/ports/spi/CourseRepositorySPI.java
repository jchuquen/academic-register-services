package co.grow.plan.academic.register.application.academicplan.course.ports.spi;

import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.generics.services.BasicRepositoryForBasicEntity;

public interface CourseRepositorySPI
    extends BasicRepositoryForBasicEntity<Course> {
}
