package co.grow.plan.academic.register.infrastructure.academicplan.period.adapters;

import co.grow.plan.academic.register.application.academicplan.period.ports.spi.PeriodRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.infrastructure.academicplan.period.entities.PeriodJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.period.mappers.InfrastructureVsDomainPeriodEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.period.repositories.PeriodJpaRepository;
import co.grow.plan.academic.register.infrastructure.shared.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class PeriodRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
            PeriodJpaEntity,
            Period,
            PeriodJpaRepository,
        InfrastructureVsDomainPeriodEntityMapper
        >
    implements PeriodRepositorySPI {

    public PeriodRepositoryAdapter(
        PeriodJpaRepository repository,
        InfrastructureVsDomainPeriodEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}
