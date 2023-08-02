package co.grow.plan.academic.register.academicplan.course.domain;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<Course, Integer> {
}
