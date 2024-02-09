package co.grow.plan.academic.register.infrastructure.academicplan.subject.repositories;

import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.JpaRepositoryForBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectJpaRepository
    extends JpaRepository<SubjectJpaEntity, Integer>,
    JpaRepositoryForBasicEntity<SubjectJpaEntity>
{
    Optional<SubjectJpaEntity> getByName(String name);
}
