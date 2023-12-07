package co.grow.plan.academic.register.infrastructure.academicplan.course.adapters;

import co.grow.plan.academic.register.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.course.model.Course;
import co.grow.plan.academic.register.infrastructure.academicplan.course.entities.CourseJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.course.mappers.IInfrastructureVsDomainCourseEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.course.repositories.CourseJPARepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class CourseRepositoryAdapter
    extends BasicRepositoryAdapter<
    CourseJpaEntity,
    Course,
    CourseJPARepository,
    IInfrastructureVsDomainCourseEntityMapper
    >
    implements ICourseRepositorySPI {

    public CourseRepositoryAdapter(CourseJPARepository repository, IInfrastructureVsDomainCourseEntityMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Optional<Course> getByName(String name) {
        var course = this.getRepository().getByName(name);
        if (course.isPresent()) {
            return Optional.of(
                this.getInfrastructureVsDomainMapper().infToDomCourse(course.get())
            );
        } else {
            return Optional.empty();
        }
    }
}
