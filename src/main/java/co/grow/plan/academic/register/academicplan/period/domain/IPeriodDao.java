package co.grow.plan.academic.register.academicplan.period.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IPeriodDao extends CrudRepository<Period, Integer> {
    Optional<Period> getByName(String name);
}
