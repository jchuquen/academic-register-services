package co.grow.plan.academic.register.application.academicplan.course.services;


import co.grow.plan.academic.register.application.academicplan.course.ports.api.ICourseServiceAPI;
import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.shared.application.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.application.exceptions.ApiError;
import co.grow.plan.academic.register.shared.application.generics.BasicService;
import org.springframework.stereotype.Service;

@Service
public final class CourseService extends BasicService<Course, ICourseRepositorySPI>
    implements ICourseServiceAPI {

    public CourseService(ICourseRepositorySPI repository) {
        super(repository);
    }

    // Validations
    @Override
    protected void validateConstrains(Integer id, Course course) {

        var optionalCourse =
            this.getRepository().getByName(course.name());

        if (id == null) { // It's creating
            if (optionalCourse.isPresent()) {
                throw new ApiConflictException(
                    new ApiError(
                        "Course with same name already exists"
                    )
                );
            }

        } else { // It's updating
            if (optionalCourse.isPresent() &&
                !id.equals(optionalCourse.get().id())) {
                throw new ApiConflictException(
                    new ApiError(
                        "Course with same name already exists"
                    )
                );
            }
        }
    }
}
