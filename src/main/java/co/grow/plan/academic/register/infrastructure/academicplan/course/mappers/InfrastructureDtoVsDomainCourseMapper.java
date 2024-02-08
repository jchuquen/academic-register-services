package co.grow.plan.academic.register.infrastructure.academicplan.course.mappers;

import co.grow.plan.academic.register.shared.infrastructure.generics.BasicInfrastructureDtoVsDomainEntityMapper;
import org.mapstruct.Mapper;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.course.dtos.CourseFullDto;

@Mapper(componentModel = "spring")
public interface InfrastructureDtoVsDomainCourseMapper
    extends BasicInfrastructureDtoVsDomainEntityMapper<
            Course,
            CourseFullDto,
            CourseCreationalDto
        > {
}
