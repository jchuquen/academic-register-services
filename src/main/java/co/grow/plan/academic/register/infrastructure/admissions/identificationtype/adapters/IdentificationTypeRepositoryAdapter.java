package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.adapters;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IIdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers.IInfrastructureVsDomainIdentificationTypeEntityMapper;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.repositories.IdentificationTypeJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class IdentificationTypeRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
            IdentificationTypeJpaEntity,
            IdentificationType,
            IdentificationTypeJpaRepository,
            IInfrastructureVsDomainIdentificationTypeEntityMapper
        >
    implements IIdentificationTypeRepositorySPI {

    public IdentificationTypeRepositoryAdapter(
        IdentificationTypeJpaRepository repository,
        IInfrastructureVsDomainIdentificationTypeEntityMapper mapper) {
        super(repository, mapper);
    }
}
