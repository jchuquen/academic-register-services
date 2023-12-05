package temp.application.academicplan.course.ports.spi;

import temp.shared.application.generics.IBasicRepository;
import temp.domain.academicplan.course.model.Course;

import java.util.Optional;

public interface ICourseRepositorySPI extends IBasicRepository<Course> {
    Optional<Course> getByName(String name);
}
