package co.grow.plan.academic.register.repositories;

import co.grow.plan.academic.register.models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Student, Integer> {
}
