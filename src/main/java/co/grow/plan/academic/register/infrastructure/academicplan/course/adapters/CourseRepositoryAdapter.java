package co.grow.plan.academic.register.infrastructure.academicplan.course.adapters;

import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.entities.CourseJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.course.mappers.IInfrastructureVsDomainCourseEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.course.repositories.CourseJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class CourseRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
        CourseJpaEntity,
        Course,
        CourseJpaRepository,
        IInfrastructureVsDomainCourseEntityMapper
    >
    implements ICourseRepositorySPI {

    public CourseRepositoryAdapter(
        CourseJpaRepository repository,
        IInfrastructureVsDomainCourseEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}
