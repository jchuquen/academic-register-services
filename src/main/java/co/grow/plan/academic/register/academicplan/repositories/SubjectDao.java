package co.grow.plan.academic.register.academicplan.repositories;

import co.grow.plan.academic.register.academicplan.models.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectDao extends CrudRepository<Subject, Integer> {
}
