package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.adapters;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers.InfrastructureVsDomainIdentificationTypeEntityMapper;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.repositories.IdentificationTypeJpaRepository;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class IdentificationTypeRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
        IdentificationTypeJpaEntity,
        IdentificationType,
        IdentificationTypeJpaRepository,
        InfrastructureVsDomainIdentificationTypeEntityMapper
    >
    implements IdentificationTypeRepositorySPI {

    public IdentificationTypeRepositoryAdapter(
        IdentificationTypeJpaRepository repository,
        InfrastructureVsDomainIdentificationTypeEntityMapper mapper) {
        super(repository, mapper);
    }
}
