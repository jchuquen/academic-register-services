package co.grow.plan.academic.register.infrastructure.academicplan.course.adapters;

import co.grow.plan.academic.register.application.academicplan.course.ports.spi.CourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.entities.CourseJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.course.mappers.InfrastructureVsDomainCourseEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.course.repositories.CourseJpaRepository;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class CourseRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
            CourseJpaEntity,
            Course,
            CourseJpaRepository,
        InfrastructureVsDomainCourseEntityMapper
        >
    implements CourseRepositorySPI {

    public CourseRepositoryAdapter(
        CourseJpaRepository repository,
        InfrastructureVsDomainCourseEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}
