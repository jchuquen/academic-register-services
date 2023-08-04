package co.grow.plan.academic.register.admissions.identificationtype.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IdentificationTypeDao extends CrudRepository<IdentificationType, Integer> {
    Optional<IdentificationType>  getByName(String name);
}
