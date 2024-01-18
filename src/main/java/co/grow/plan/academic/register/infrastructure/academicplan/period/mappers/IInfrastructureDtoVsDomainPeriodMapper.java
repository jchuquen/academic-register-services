package co.grow.plan.academic.register.infrastructure.academicplan.period.mappers;

import co.grow.plan.academic.register.domain.academicplan.period.model.Period;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodCreationalDto;
import co.grow.plan.academic.register.infrastructure.academicplan.period.dtos.PeriodFullDto;
import co.grow.plan.academic.register.shared.infrastructure.generics.IBasicInfrastructureDtoVsDomainEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInfrastructureDtoVsDomainPeriodMapper
    extends IBasicInfrastructureDtoVsDomainEntityMapper<
    Period,
        PeriodFullDto,
        PeriodCreationalDto
    > {
}
