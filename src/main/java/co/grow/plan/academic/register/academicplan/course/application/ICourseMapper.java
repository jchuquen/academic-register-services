package co.grow.plan.academic.register.academicplan.course.application;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.shared.generics.IBasicMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICourseMapper
    extends IBasicMapper<
    Course,
    CourseDto,
    CourseNewDto
    > {
}