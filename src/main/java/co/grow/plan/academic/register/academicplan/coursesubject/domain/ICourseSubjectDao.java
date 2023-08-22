package co.grow.plan.academic.register.academicplan.coursesubject.domain;

import co.grow.plan.academic.register.academicplan.course.domain.Course;
import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ICourseSubjectDao extends CrudRepository<CourseSubject, Integer> {
    Optional<CourseSubject> getByCourseAndSubject(Course course, Subject subject);
}
