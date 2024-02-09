package co.grow.plan.academic.register.infrastructure.academicplan.course.mappers;

import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.entities.CourseJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InfrastructureVsDomainCourseEntityMapper
    extends BasicInfrastructureVsDomainEntityMapper<
            Course,
            CourseJpaEntity
        > {
}
