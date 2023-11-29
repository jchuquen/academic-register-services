package temp.infrastructure.academicplan.course;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseJPARepository extends JpaRepository<Course, Integer> {
    Optional<Course> getByName(String name);
}
