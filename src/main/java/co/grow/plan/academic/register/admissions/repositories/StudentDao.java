package co.grow.plan.academic.register.admissions.repositories;

import co.grow.plan.academic.register.admissions.models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Student, Integer> {
}
