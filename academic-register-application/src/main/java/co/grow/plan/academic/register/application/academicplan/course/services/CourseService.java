package co.grow.plan.academic.register.application.academicplan.course.services;


import co.grow.plan.academic.register.application.academicplan.course.ports.api.CourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.CourseRepositorySPI;
import co.grow.plan.academic.register.application.shared.generics.services.BasicServiceForBasicEntity;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;

public final class CourseService
    extends BasicServiceForBasicEntity<Course, CourseRepositorySPI>
    implements CourseServiceAPI {

    public CourseService(CourseRepositorySPI repository) {
        super(repository);
    }
}
