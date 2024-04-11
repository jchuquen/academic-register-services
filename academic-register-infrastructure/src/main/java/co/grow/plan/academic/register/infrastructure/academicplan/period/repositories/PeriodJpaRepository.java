package co.grow.plan.academic.register.infrastructure.academicplan.period.repositories;

import co.grow.plan.academic.register.infrastructure.academicplan.period.entities.PeriodJpaEntity;
import co.grow.plan.academic.register.infrastructure.shared.generics.JpaRepositoryForBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodJpaRepository
    extends JpaRepository<PeriodJpaEntity, Integer>,
    JpaRepositoryForBasicEntity<PeriodJpaEntity>
{
    Optional<PeriodJpaEntity> getByName(String name);
}
