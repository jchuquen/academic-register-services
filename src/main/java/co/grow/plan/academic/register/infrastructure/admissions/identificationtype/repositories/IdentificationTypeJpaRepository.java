package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.repositories;

import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.JpaRepositoryForBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentificationTypeJpaRepository
    extends JpaRepository<IdentificationTypeJpaEntity,Integer>,
    JpaRepositoryForBasicEntity<IdentificationTypeJpaEntity> {
    Optional<IdentificationTypeJpaEntity> getByName(String name);
}
