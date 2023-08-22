package co.grow.plan.academic.register.academicplan.period.application;

import co.grow.plan.academic.register.academicplan.period.domain.Period;
import co.grow.plan.academic.register.shared.generics.IBasicMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPeriodMapper
    extends IBasicMapper<
        Period,
        PeriodDto,
        PeriodNewDto
    > {
}
