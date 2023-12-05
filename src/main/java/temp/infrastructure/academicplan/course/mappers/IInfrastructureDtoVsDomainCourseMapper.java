package temp.infrastructure.academicplan.course.mappers;

import temp.domain.academicplan.course.model.Course;
import temp.shared.infrastructure.generics.IBasicInfrastructureDtoVsDomainEntityMapper;

public interface IInfrastructureDtoVsDomainCourseMapper
    extends IBasicInfrastructureDtoVsDomainEntityMapper<
        Course,
        CourseFullDto,
        CourseCreationalDto
    > {
}
