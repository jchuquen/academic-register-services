package co.grow.plan.academic.register.infrastructure.academicplan.course.mappers;

import co.grow.plan.academic.register.infrastructure.academicplan.course.entities.CourseJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;

@Mapper(componentModel = "spring")
public interface IInfrastructureVsDomainCourseEntityMapper
    extends IBasicInfrastructureVsDomainEntityMapper<
        Course,
        CourseJpaEntity
    > {
}
