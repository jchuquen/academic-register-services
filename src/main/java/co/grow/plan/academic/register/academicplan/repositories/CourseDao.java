package co.grow.plan.academic.register.academicplan.repositories;

import co.grow.plan.academic.register.academicplan.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<Course, Integer> {
}
