package co.grow.plan.academic.register.infrastructure.academicplan.course.mappers;

import org.mapstruct.Mapper;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureDtoVsDomainEntityMapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureDtoVsDomainCourseMapper
    extends IBasicInfrastructureDtoVsDomainEntityMapper<
        Course,
        CourseFullDto,
        CourseCreationalDto
    > {
}
