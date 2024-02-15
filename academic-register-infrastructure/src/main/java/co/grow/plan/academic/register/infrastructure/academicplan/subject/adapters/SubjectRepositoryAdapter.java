package co.grow.plan.academic.register.infrastructure.academicplan.subject.adapters;

import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.SubjectRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers.InfrastructureVsDomainSubjectEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.repositories.SubjectJpaRepository;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class SubjectRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
        SubjectJpaEntity,
        Subject,
        SubjectJpaRepository,
        InfrastructureVsDomainSubjectEntityMapper
    >
    implements SubjectRepositorySPI {

    public SubjectRepositoryAdapter(
        SubjectJpaRepository repository,
        InfrastructureVsDomainSubjectEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}
