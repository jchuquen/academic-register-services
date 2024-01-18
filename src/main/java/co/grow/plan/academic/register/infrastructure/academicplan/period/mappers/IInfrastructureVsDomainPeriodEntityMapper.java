package co.grow.plan.academic.register.infrastructure.academicplan.period.mappers;

import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.infrastructure.academicplan.period.entities.PeriodJpaEntity;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureVsDomainPeriodEntityMapper
    extends IBasicInfrastructureVsDomainEntityMapper<
        Period,
        PeriodJpaEntity
    > {
}
