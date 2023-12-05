package temp.infrastructure.academicplan.course.adapters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import temp.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import temp.domain.academicplan.course.model.Course;
import temp.infrastructure.academicplan.course.entities.CourseEntity;
import temp.infrastructure.academicplan.course.mappers.IInfrastructureVsDomainCourseEntityMapper;
import temp.infrastructure.academicplan.course.repositories.CourseJPARepository;
import temp.shared.infrastructure.generics.BasicRepositoryAdapter;

import java.util.Optional;

@Service
@AllArgsConstructor
public final class CourseRepositoryAdapter
    extends BasicRepositoryAdapter<
    CourseEntity,
    Course,
    CourseJPARepository,
    IInfrastructureVsDomainCourseEntityMapper
    >
    implements ICourseRepositorySPI {

    @Override
    public Optional<Course> getByName(String name) {
        var course = repository.getByName(name);
        if (course.isPresent()) {
            return Optional.of(
                infrastructureVsDomainMapper.infToDomCourse(course.get())
            );
        } else {
            return Optional.empty();
        }
    }
}
