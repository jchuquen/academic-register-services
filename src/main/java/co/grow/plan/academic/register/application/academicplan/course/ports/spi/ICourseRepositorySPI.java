package co.grow.plan.academic.register.application.academicplan.course.ports.spi;

import co.grow.plan.academic.register.shared.application.generics.IBasicRepository;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;

import java.util.Optional;

public interface ICourseRepositorySPI extends IBasicRepository<Course> {
    Optional<Course> getByName(String name);
}
