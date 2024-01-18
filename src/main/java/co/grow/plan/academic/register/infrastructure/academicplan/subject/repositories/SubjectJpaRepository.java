package co.grow.plan.academic.register.infrastructure.academicplan.subject.repositories;

import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IJpaRepositoryForBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectJpaRepository
    extends JpaRepository<SubjectJpaEntity, Integer>,
    IJpaRepositoryForBasicEntity<SubjectJpaEntity>
{
    Optional<SubjectJpaEntity> getByName(String name);
}
