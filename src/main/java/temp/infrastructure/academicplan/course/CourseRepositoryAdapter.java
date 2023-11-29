package temp.infrastructure.academicplan.course;

import temp.application.academicplan.course.ports.spi.ICourseRepositorySPI;
import temp.domain.academicplan.course.model.Course;

import java.util.List;
import java.util.Optional;

public class CourseRepositoryAdapter implements ICourseRepositorySPI {
    @Override
    public Optional<Course> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public Optional<Course> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Course save(Course entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
