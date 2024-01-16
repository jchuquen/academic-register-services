package co.grow.plan.academic.register.application.academicplan.course.services;


import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.generics.services.BasicServiceForBasicEntity;

public final class CourseService
    extends BasicServiceForBasicEntity<Course, ICourseRepositorySPI>
    implements ICourseServiceAPI {

    public CourseService(ICourseRepositorySPI repository) {
        super(repository);
    }
}
