package co.grow.plan.academic.register.academicplan.subject.domain;

import co.grow.plan.academic.register.academicplan.subject.domain.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectDao extends CrudRepository<Subject, Integer> {
}
