package co.grow.plan.academic.register.infrastructure.academicplan.course.mappers;

import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicInfrastructureDtoVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfrastructureDtoVsDomainCourseMapper
    extends BasicInfrastructureDtoVsDomainEntityMapper<
        Course,
        CourseFullDto,
        CourseCreationalDto
    > {
}
