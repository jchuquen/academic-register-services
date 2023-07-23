package co.grow.plan.academic.register.admissions.repositories;

import co.grow.plan.academic.register.admissions.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Person, Integer> {
}
