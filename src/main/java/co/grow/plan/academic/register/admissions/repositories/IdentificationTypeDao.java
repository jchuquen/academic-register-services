package co.grow.plan.academic.register.admissions.repositories;

import co.grow.plan.academic.register.admissions.models.IdentificationType;
import org.springframework.data.repository.CrudRepository;

public interface IdentificationTypeDao extends CrudRepository<IdentificationType, Integer> {
    IdentificationType getByName(String name);
}
