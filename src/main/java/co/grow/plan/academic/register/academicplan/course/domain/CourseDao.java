package co.grow.plan.academic.register.academicplan.course.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseDao extends CrudRepository<Course, Integer> {
    Optional<Course> getByName(String name);
}
