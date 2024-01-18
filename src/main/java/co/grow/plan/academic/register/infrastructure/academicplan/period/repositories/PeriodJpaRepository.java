package co.grow.plan.academic.register.infrastructure.academicplan.period.repositories;

import co.grow.plan.academic.register.infrastructure.academicplan.period.entities.PeriodJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IJpaRepositoryForBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodJpaRepository
    extends JpaRepository<PeriodJpaEntity, Integer>,
    IJpaRepositoryForBasicEntity<PeriodJpaEntity>
{
    Optional<PeriodJpaEntity> getByName(String name);
}
