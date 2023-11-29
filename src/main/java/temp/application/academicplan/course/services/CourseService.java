package temp.application.academicplan.course.services;


import lombok.AllArgsConstructor;
import temp.application.academicplan.course.ports.api.ICourseServiceAPI;
import temp.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import temp.shared.exceptions.ApiConflictException;
import temp.shared.exceptions.ApiError;
import temp.shared.generics.BasicService;
import temp.domain.academicplan.course.model.Course;

import java.util.Optional;

@AllArgsConstructor
public class CourseService extends BasicService<Course, ICourseRepositorySPI>
    implements ICourseServiceAPI {

    // Validations
    @Override
    protected void validateConstrains(Integer id, Course course) {

        Optional<Course> optionalCourse =
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
