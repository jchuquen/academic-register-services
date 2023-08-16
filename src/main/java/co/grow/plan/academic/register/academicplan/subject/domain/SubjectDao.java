package co.grow.plan.academic.register.academicplan.subject.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubjectDao extends CrudRepository<Subject, Integer> {
    Optional<Subject> getByName(String name);
}
