package co.grow.plan.academic.register.repositories;

import co.grow.plan.academic.register.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<Course, Integer> {
}
