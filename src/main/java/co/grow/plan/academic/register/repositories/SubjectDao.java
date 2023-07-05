package co.grow.plan.academic.register.repositories;

import co.grow.plan.academic.register.models.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectDao extends CrudRepository<Subject, Integer> {
}
