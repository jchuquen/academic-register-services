package co.grow.plan.academic.register.infrastructure.admissions.identificationtype.adapters;

import co.grow.plan.academic.register.application.admissions.identificationtype.ports.spi.IIdentificationTypeRepositorySPI;
import co.grow.plan.academic.register.domain.admissions.identificationtype.model.IdentificationType;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.entities.IdentificationTypeJpaEntity;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.mappers.IInfrastructureVsDomainIdentificationTypeEntityMapper;
import co.grow.plan.academic.register.infrastructure.admissions.identificationtype.repositories.IdentificationTypeJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class IdentificationTypeRepositoryAdapter
    extends BasicRepositoryAdapter<
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

    @Override
    public Optional<IdentificationType> getByName(String name) {
        var identificationType =
            this.getRepository().getByName(name);
        if (identificationType.isPresent()) {
            return Optional.of(
                this.getInfrastructureVsDomainMapper()
                    .infToDomCourse(identificationType.get())
            );
        } else {
            return Optional.empty();
        }
    }
}
