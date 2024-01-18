package co.grow.plan.academic.register.infrastructure.academicplan.period.adapters;

import co.grow.plan.academic.register.application.academicplan.period.ports.spi.IPeriodRepositorySPI;
import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.infrastructure.academicplan.period.entities.PeriodJpaEntity;
import co.grow.plan.academic.register.infrastructure.academicplan.period.mappers.IInfrastructureVsDomainPeriodEntityMapper;
import co.grow.plan.academic.register.infrastructure.academicplan.period.repositories.PeriodJpaRepository;
import co.grow.plan.academic.register.shared.infrastructure.generics.BasicRepositoryAdapterForBasicEntity;
import org.springframework.stereotype.Service;

@Service
public final class PeriodRepositoryAdapter
    extends BasicRepositoryAdapterForBasicEntity<
        PeriodJpaEntity,
        Period,
        PeriodJpaRepository,
        IInfrastructureVsDomainPeriodEntityMapper
    >
    implements IPeriodRepositorySPI {

    public PeriodRepositoryAdapter(
        PeriodJpaRepository repository,
        IInfrastructureVsDomainPeriodEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}
