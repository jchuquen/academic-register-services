package co.grow.plan.academic.register.infrastructure.academicplan.subject.adapters;

import co.grow.plan.academic.register.application.academicplan.subject.ports.spi.ISubjectRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.subject.model.Subject;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.entities.SubjectJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.mappers.IInfrastructureVsDomainSubjectEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.subject.repositories.SubjectJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class SubjectRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
        SubjectJpaEntity,
        Subject,
        SubjectJpaRepository,
    IInfrastructureVsDomainSubjectEntityMapper
    >
    implements ISubjectRepositorySPI {

    public SubjectRepositoryAdapter(
        SubjectJpaRepository repository,
        IInfrastructureVsDomainSubjectEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}
