package temp.application.academicplan.course.services;


import lombok.AllArgsConstructor;
import temp.application.academicplan.course.ports.api.ICourseServiceAPI;
import temp.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import temp.domain.academicplan.course.model.Course;
import temp.shared.application.exceptions.ApiConflictException;
import temp.shared.application.exceptions.ApiError;
import temp.shared.application.generics.BasicService;

@AllArgsConstructor
public final class CourseService extends BasicService<Course, ICourseRepositorySPI>
    implements ICourseServiceAPI {

    // Validations
    @Override
    protected void validateConstrains(Integer id, Course course) {

        var optionalCourse =
            super.repository.getByName(course.name());

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
