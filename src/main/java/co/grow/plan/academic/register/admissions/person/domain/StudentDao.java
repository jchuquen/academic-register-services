package co.grow.plan.academic.register.admissions.person.domain;

import co.grow.plan.academic.register.admissions.person.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface StudentDao extends CrudRepository<Person, Integer> {
}
