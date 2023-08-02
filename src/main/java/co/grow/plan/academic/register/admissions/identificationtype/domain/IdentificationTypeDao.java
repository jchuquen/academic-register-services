package co.grow.plan.academic.register.admissions.identificationtype.domain;

import co.grow.plan.academic.register.admissions.identificationtype.domain.IdentificationType;
import org.springframework.data.repository.CrudRepository;

public interface IdentificationTypeDao extends CrudRepository<IdentificationType, Integer> {
    IdentificationType getByName(String name);
}
