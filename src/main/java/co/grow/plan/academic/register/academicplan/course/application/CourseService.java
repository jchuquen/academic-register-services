package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.academicplan.course.domain.ICourseDao;
import co.grow.plan.academic.register.shared.exceptions.ApiConflictException;
import co.grow.plan.academic.register.shared.exceptions.ApiError;
import co.grow.plan.academic.register.shared.generics.BasicService;
import co.grow.plan.academic.register.shared.generics.IValidable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService
    extends BasicService<
    Course,
    ICourseDao,
    CourseDto,
    CourseNewDto,
    ICourseMapper
    >
    implements ICourseService {

    public CourseService(ICourseDao dao, ICourseMapper mapper) {
        super(dao, mapper);
    }

    // Validations
    @Override
    protected void validateConstrains(Integer id, IValidable dto) {

        CourseNewDto courseNewDto = (CourseNewDto) dto;

        Optional<Course> optionalCourse =
            super.dao.getByName(courseNewDto.getName());

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
                !id.equals(optionalCourse.get().getId())) {
                throw new ApiConflictException(
                    new ApiError(
                        "Course with same name already exists"
                    )
                );
            }
        }
    }
}